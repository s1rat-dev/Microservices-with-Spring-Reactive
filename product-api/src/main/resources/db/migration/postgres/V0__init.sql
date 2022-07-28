CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.products
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    price bigint NOT NULL,
    stock integer NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
)