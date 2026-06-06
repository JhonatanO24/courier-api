CREATE TABLE shipments (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    sender_id       UUID NOT NULL REFERENCES customers(id),
    recipient_id    UUID NOT NULL REFERENCES customers(id),
    declared_value  DECIMAL(15,2) NOT NULL,
    shipping_cost   DECIMAL(15,2) NOT NULL,
    type            VARCHAR(30) NOT NULL,
    status          VARCHAR(30) NOT NULL,
    metadata        JSONB NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);