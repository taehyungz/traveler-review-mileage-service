CREATE TABLE point
(
    point_id   BIGINT AUTO_INCREMENT NOT NULL,
    user_id    VARCHAR(36)           NOT NULL,
    amount     INT                   NOT NULL,
    version    BIGINT                NOT NULL,
    created_at datetime              NOT NULL,
    updated_at datetime              NOT NULL,
    CONSTRAINT pk_point PRIMARY KEY (point_id)
);

ALTER TABLE point
    ADD CONSTRAINT uc_point_userid UNIQUE (user_id);


CREATE TABLE point_event
(
    point_event_id BIGINT AUTO_INCREMENT NOT NULL,
    point_id       BIGINT                NOT NULL,
    review_id      VARCHAR(36)           NOT NULL,
    user_id        VARCHAR(36)           NOT NULL,
    reason         VARCHAR(255)          NOT NULL,
    amount         INT                   NOT NULL,
    version        BIGINT                NOT NULL,
    created_at     datetime              NOT NULL,
    updated_at     datetime              NOT NULL,
    CONSTRAINT pk_point_event PRIMARY KEY (point_event_id)
);

CREATE INDEX idx_point_event_review_id ON point_event (review_id);

CREATE INDEX idx_point_event_user_id ON point_event (user_id);

ALTER TABLE point_event
    ADD CONSTRAINT FK_POINT_EVENT_ON_POINT FOREIGN KEY (point_id) REFERENCES point (point_id);


CREATE TABLE review
(
    review_id  VARCHAR(36) NOT NULL,
    user_id    VARCHAR(36) NOT NULL,
    place_id   VARCHAR(36) NOT NULL,
    created_at datetime    NOT NULL,
    updated_at datetime    NOT NULL,
    CONSTRAINT pk_review PRIMARY KEY (review_id)
);

CREATE INDEX idx_review_user_id ON review (user_id);

CREATE INDEX idx_review_place_id ON review (place_id);


CREATE TABLE photo
(
    photo_id   VARCHAR(36) NOT NULL,
    review_id  VARCHAR(36) NOT NULL,
    created_at datetime    NOT NULL,
    updated_at datetime    NOT NULL,
    CONSTRAINT pk_photo PRIMARY KEY (photo_id)
);

ALTER TABLE photo
    ADD CONSTRAINT FK_PHOTO_ON_REVIEW FOREIGN KEY (review_id) REFERENCES review (review_id);