CREATE TABLE locations
(
    id                  serial,
    city                varchar NOT NULL,
    district            varchar NOT NULL,
    country             varchar NOT NULL,
    village             varchar,
    street              varchar NOT NULL,
    bloc                varchar,
    stair               varchar,
    no                  varchar NOT NULL,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key (id)
);

CREATE TABLE vegetables
(
    id                  serial,
    name                varchar not null,
    category            varchar not null,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    PRIMARY KEY (id),
    UNIQUE (name)

);

CREATE TABLE clients
(
    id                  serial,
    first_name          varchar NOT NULL,
    last_name           varchar NOT NULL,
    phone               varchar NOT NULL,
    email               varchar NOT NULL,
    password            varchar,
    location_id         int     NOT NULL,
    is_admin            boolean                     DEFAULT false,
    valid_account       boolean NOT NULL            DEFAULT FALSE,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key (id),
    unique (email,phone),
    FOREIGN KEY (location_id) REFERENCES locations (id)

);

CREATE TABLE producers
(
    id                  serial,
    client_id           int     NOT NULL,
    document            varchar NOT NULL,
    description         varchar ,
    active              boolean NOT NULL            DEFAULT FALSE,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key (id),
    unique (client_id)
);

CREATE TABLE notices
(
    id                  serial,
    producer_id         int NOT NULL,
    vegetable_id        int NOT NULL,
    quantity_available  int NOT NULL,
    unit                varchar NOT NULL,
    image               varchar,
    home_delivery       boolean NOT NULL            DEFAULT FALSE,
    product_notice      varchar,
    valid               boolean NOT NULL            DEFAULT FALSE,
    alive               boolean NOT NULL            DEFAULT FALSE,
    price_per_unit      INTEGER NOT NULL,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key(id),
    FOREIGN KEY (producer_id) REFERENCES producers (id),
    FOREIGN KEY (vegetable_id) REFERENCES vegetables (id)
);

CREATE TABLE feedbacks
(
    client_id           int NOT NULL,
    notices_id          int NOT NULL,
    message             varchar,
    qualifying          int NOT NULL,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key (client_id,notices_id),
    FOREIGN KEY (client_id) REFERENCES clients (id),
    FOREIGN KEY (notices_id) REFERENCES notices (id)
);

CREATE TABLE orders
(
    id                  serial,
    client_id           int NOT NULL,
    confirmed           boolean NOT NULL           DEFAULT FALSE,
    notice_order        varchar,
    date                timestamp without time zone DEFAULT now(),
    total_price         int  NOT NULL,
    location_id         int,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key (id),
    FOREIGN KEY (client_id) REFERENCES clients (id),
    FOREIGN KEY (location_id) REFERENCES locations (id)
);


CREATE TABLE products
(
    id                  serial,
    notice_id           int NOT NULL,
    quantity            int NOT NULL,
    mode_delivery       varchar NOT NULL,
    order_id            int NOT NULL,
    date_created        timestamp without time zone DEFAULT now(),
    date_updated        timestamp without time zone DEFAULT now(),
    is_deleted          boolean NOT NULL            DEFAULT FALSE,
    primary key (id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);











