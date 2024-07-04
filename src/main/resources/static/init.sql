create schema if not exists ecommerce;
use ecommerce;


CREATE TABLE user (
                      id VARCHAR(255) NOT NULL PRIMARY KEY,
                      user_name VARCHAR(100) NOT NULL UNIQUE,
                      first_name VARCHAR(255) NOT NULL,
                      full_name VARCHAR(100) NOT NULL,
                      gender VARCHAR(255) NOT NULL,
                      mobile VARCHAR(20) NOT NULL,
                      password VARCHAR(100) NOT NULL,
                      photo LONGTEXT  NULL,
                      address VARCHAR(100) NOT NULL,
                      birth_date VARCHAR(255) NOT NULL,
                      email VARCHAR(100) NOT NULL,
                      status VARCHAR(20) NOT NULL
);


create table role
(
    role_id bigint auto_increment
        primary key,
    name    varchar(255) null
);
create table user_role
(
    id      bigint auto_increment
        primary key,
    role_id bigint      not null,
    user_id varchar(255) not null,
    foreign key (role_id) references role(role_id),
    foreign key (user_id) references user(id)
);
CREATE TABLE category (
                          category_id bigint AUTO_INCREMENT PRIMARY KEY,
                          is_activated BIT NOT NULL,
                          is_deleted BIT NOT NULL,
                          name VARCHAR(255) NOT NULL UNIQUE
);
create table product
(
    product_id       bigint auto_increment
        primary key,
    category_id      bigint          null,
    cost_price       double       null,
    current_quantity int          null,
    description      varchar(255) null,
    image            varchar(255) null,
    is_activated     bit          not null,
    is_deleted       bit          not null,
    product_name     varchar(255) null,
    foreign key(category_id) references category(category_id)
);
create table shopping_cart
(
    id      bigint auto_increment
        primary key,
    user_id varchar(255) not null ,
    foreign key (user_id) references user(id)

);
create table cart_item
(
    id         bigint auto_increment
        primary key,
    cart_id    bigint not null,
    product_id bigint  not null ,
    quantity   int not null,
    foreign key (cart_id) references shopping_cart(id),
    foreign key (product_id) references product(product_id)
);
create table orders
(
    id            bigint auto_increment
        primary key,
    user_id   varchar(255)           not null,
    delivery_date varchar(255) not null,
    notes         varchar(255) not null,
    order_date    varchar(255) not null,
    order_status  varchar(255) not null,
    shipping_fee  double       not null,
    total_price   double       not null,
    foreign key (user_id) references user(id)

);


create table order_item
(
    id          bigint auto_increment
        primary key,
    order_id    bigint not null ,
    product_id  bigint not null ,
    quantity    int    not null,
    total_price double  not null
);






