package com.product.promotion.features.order;

import com.product.promotion.features.client.contract.ClientContract;
import com.product.promotion.features.location.contract.LocationContract;
import com.product.promotion.features.order.contract.OrderContract;
import com.product.promotion.features.product.ProductDto;
import com.product.promotion.features.product.contract.ProductsContract;
import com.product.promotion.utils.SendEmail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderContract {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;
    private ClientContract clientContract;
    private LocationContract locationContract;
    private SendEmail sendEmail;
    private ProductsContract productsContract;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, ClientContract clientContract,
                        LocationContract locationContract, SendEmail sendEmail, ProductsContract productsContract) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.clientContract = clientContract;
        this.locationContract = locationContract;
        this.sendEmail = sendEmail;
        this.productsContract = productsContract;
        modelMapper.addMappings(Utils.orderFieldMapping);
        modelMapper.addMappings(Utils.orderMapping);
    }



    /**
     * Creates a new entity in the database.
     *
     * @param dto The  DTO object will all information for creating the new object
     * @return A DTO object which contains information about the new object.
     */
    OrderDto initiate (@NotNull OrderDto dto) {
        Order order = modelMapper.map(dto, Order.class);
        order.setClientId(clientContract.getClientById(dto.getClientId()));
        order.setLocationId(locationContract.getLocationById(dto.getLocationId()));
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }



    ResponseEntity<String> placeOrderAndSentEmail (@NotNull Integer orderId) {

        StringBuilder mailClient = new StringBuilder(String.format("%32s%32s%32s%32s%32s%32s", "Produs", "Cantitate", "Pret", "Producator","Telefon","Email"));
        mailClient.append("\n");

        Optional<Order> order = orderRepository
                .findById(orderId);

        List<ProductDto> productDto = productsContract.getAllByOrder(orderId);

        if(productDto.isEmpty())
            return ResponseEntity.badRequest().body("Adaugati cel putin un produs!");
        else
        {
            for (ProductDto product: productDto) {
                StringBuilder mailProducer = new StringBuilder(String.format("%32s%32s%32s%32s%32s", "Produs", "Cantitate", "Client","Telefon","Email"));
                mailProducer.append("\n");
                mailClient.append(String.format("%32s%32s%32s%32s%32s%32s",
                        product.getVegetableName(),
                        product.getQuantity(),
                        product.getPricePerUnit() * product.getQuantity(),
                        product.getProducerFirstName() + " " + product.getProducerLastName(),
                        product.getProducerPhone(),product.getProducerEmail()+"\n"));
                mailProducer.append(String.format("%32s%32s%32s%32s%32s",
                        product.getVegetableName(),
                        product.getQuantity(),
                        order.get().getClientId().getFirstName() + " " + order.get().getClientId().getLastName(),
                        order.get().getClientId().getPhone(),
                        order.get().getClientId().getEmail()+"\n"+"\n"));
                mailProducer.append("Adresa clientului").append("\n").append("Tara: "+order.get().getClientId().getLocationId().getCountry())
                        .append("\n"+"Oras: "+order.get().getClientId().getLocationId().getCity())
                        .append("\n"+"Comuna: "+order.get().getClientId().getLocationId().getVillage())
                        .append("\n"+"District: "+order.get().getClientId().getLocationId().getDistrict())
                        .append("\n"+"Strada: "+order.get().getClientId().getLocationId().getStreet())
                        .append("\n"+"Bloc: "+order.get().getClientId().getLocationId().getBloc())
                        .append("\n"+"Scara: "+order.get().getClientId().getLocationId().getStair())
                        .append("\n"+"Nr: "+order.get().getClientId().getLocationId().getNo())
                        .append("\n"+"Detalii: "+order.get().getDetails())
                        .append("\n"+"Modul de livrare: "+product.getDeliveryMode());
                sendEmail.sendMail(product.getProducerEmail(), "Comanda noua: ", mailProducer.toString());
            }
            mailClient.append("\n");
            mailClient.append("\n");
            mailClient.append("Pret total: ").append(order.get().getTotalPrice()).append(" lei").append("\n");
            mailClient.append("Detalii: ").append(order.get().getDetails()).append("\n");
            sendEmail.sendMail(order.get().getClientId().getEmail(), "Comanda dumneavoastra: "
                    , mailClient.toString());
            return ResponseEntity.ok("Comanda plasata cu succes!");
        }

    }

    /**
     * Returns a list of all non soft-deleted entities from the database.
     *
     * @return A list of DTO objects with details of the non soft-deleted entities stored in the database.
     */
    List<OrderDto> getAll() {
        return orderRepository
                .findAllByIsDeletedFalse()
                .stream()
                .map(item -> modelMapper.map(item, OrderDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    @Override
    public OrderDto getById(@NotNull Integer id) {
        return orderRepository
                .findById(id)
                .map(result -> modelMapper.map(result, OrderDto.class))
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Updates an existing entity from the database.
     *
     * @param dto The DTO object which contains all the information for the entity.
     * @return A DTO object updated with the information which has stored in the database.
     */
    @Override
    public OrderDto update(@NotNull OrderDto dto) {
        return orderRepository
                .findById(dto.getId())
                .map(result -> {
                    Order toBeUpdated = modelMapper.map(dto, Order.class);
                    return modelMapper.map(orderRepository.save(toBeUpdated), OrderDto.class);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Soft-deletes an existing entity from the database.
     *
     * @param id The ID of the entity stored in the database.
     * @return TRUE if the entity was soft-deleted, FALSE otherwise.
     */
     Boolean delete(@NotNull Integer id) {
        return orderRepository
                .findById(id)
                .map(result -> {
                    result.setIsDeleted(true);
                    return orderRepository.save(result).getIsDeleted();
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Gets an entity from the database based on its ID.
     *
     * @param id The ID of the entity stored in the database.
     * @return A DTO object which contains information about the requested entity.
     */
    @Override
    public Order getOrderById(Integer id) {
        return orderRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

}
