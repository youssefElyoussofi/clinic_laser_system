CREATE TABLE users
(
    id                 UUID PRIMARY KEY          DEFAULT gen_random_uuid(),

    name               VARCHAR(125)     NOT NULL,

    date_of_birth      DATE             NOT NULL,
    role               VARCHAR(20)      NOT NULL DEFAULT 'PATIENT_USER' CHECK (role IN ('PATIENT_USER', 'CENTER_STAFF', 'SYSTEM_ADMIN')),
    phone_number       VARCHAR(20),
    last_laser_date TIMESTAMPTZ,
    laser_count    INTEGER          NOT NULL DEFAULT 0,
    next_eligible_date TIMESTAMPTZ,
    created_at         TIMESTAMPTZ      NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ      NOT NULL DEFAULT now()
);

CREATE TABLE centers
(
    id                   UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    center_name          VARCHAR(255) NOT NULL,
    center_city_location VARCHAR(125) NOT NULL,
    physical_address     VARCHAR(255) NOT NULL,
    phone_number         VARCHAR(20),
    opening_hours        TEXT,
    created_at           TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at           TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE center_staff
(
    id          UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    user_id     UUID        NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    center_id   UUID        NOT NULL  REFERENCES centers (id) ON DELETE CASCADE,
    assigned_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE slots
(
    id           UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    center_id    UUID        NOT NULL REFERENCES centers (id) ON DELETE CASCADE,
    starts_at    TIMESTAMPTZ NOT NULL,
    ends_at      TIMESTAMPTZ NOT NULL,
    capacity     INTEGER     NOT NULL CHECK (capacity > 0),
    booked_count INTEGER     NOT NULL DEFAULT 0 CHECK (booked_count >= 0),
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT uq_slot_center_start UNIQUE (center_id, starts_at),
    CONSTRAINT ck_slot_time  CHECK (ends_at > starts_at),
    CONSTRAINT ck_slot_capacity CHECK (booked_count <= capacity),
    CONSTRAINT uq_slot_id_center UNIQUE (id, center_id)
);

CREATE TABLE appointments
(
    id                      UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
    user_id                 UUID               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    center_id               UUID               NOT NULL REFERENCES centers (id) ON DELETE RESTRICT,
    slot_id                 UUID               NOT NULL REFERENCES slots (id) ON DELETE RESTRICT,
    scheduled_date_and_time TIMESTAMPTZ        NOT NULL,
    appointment_status VARCHAR(30)        NOT NULL DEFAULT 'PENDING_WAITING' CHECK (appointment_status IN ('PENDING_WAITING', 'COMPLETED_AND_VALIDATED','CANCELLED', 'DID_NOT_SHOW_UP')),
    validated_by_user_id    UUID               REFERENCES users (id) ON DELETE SET NULL,
    validated_at            TIMESTAMPTZ,
    created_at              TIMESTAMPTZ        NOT NULL DEFAULT now(),

    CONSTRAINT uq_appointment_user_slot UNIQUE (user_id, slot_id),
    CONSTRAINT fk_appointment_slot_center FOREIGN KEY (slot_id, center_id) REFERENCES slots (id, center_id)
);