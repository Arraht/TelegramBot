-- liquibase formated sql

-- changeset Igor:1
CREATE TABLE Notification_Task(
first_key INTEGER,
chat_id INTEGER,
message TEXT,
date_message TIMESTAMP
)