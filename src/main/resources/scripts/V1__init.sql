CREATE TABLE shelter
(
    name        TEXT,
    type        TEXT PRIMARY KEY,
    description TEXT,
    instruction TEXT
);
INSERT INTO shelter (name, description, type, instruction)
VALUES ('Классический гороскоп (шелтер нейм)', 'обычный гороскоп инфо пишем тут (дескрипшн)', 'CLASSIC',
'инструкции (инстракшн)'),
    ('гороскоп китайский (нейм)', 'гороскоп китайский (дискрипшн)', 'CHINA',
'Инструкция по кит гороскопу');

CREATE TABLE users
(
    id      BIGINT PRIMARY KEY,
    name    TEXT,
    email   TEXT,
    phone   TEXT,
    chat_id  BIGINT

);

CREATE TABLE report
(
    id         BIGINT,
    users_id    BIGINT,
    report_text TEXT,
    FOREIGN KEY (users_id) REFERENCES users(id)
)