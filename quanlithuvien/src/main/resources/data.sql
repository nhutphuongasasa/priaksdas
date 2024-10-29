
INSERT INTO roles (name) 
VALUES 
    ('admin'),
    ('staff');

INSERT INTO admins (user_name, password, name, email, phone, role_id) 
VALUES 
    ('adminUser', '$2a$10$iLBIf2EpjQy.8ggfqiLxUOanRUjzL7N6PzoeB5YGGGjMotLXCuJLK', 'admin', 'admin@example.com', '12345678', 1);

INSERT INTO books (title, author, publisher, publication_year, isbn, quantity, available_quantity) 
VALUES 
    ('The Alchemist', 'Paulo Coelho', 'Ho Chi Minh City General Publishing House', 2015, '978-604-68-0124-6', 20, 20),
    ('How to Win Friends and Influence People', 'Dale Carnegie', 'Labour Publishing House', 2016, '978-604-68-0125-3', 15, 15),
    ('Norwegian Wood', 'Haruki Murakami', 'Kim Dong Publishing House', 2018, '978-604-90-1234-5', 10, 10);

INSERT INTO categories (name) 
VALUES 
    ('Novels'),
    ('Life Skills'),
    ('Psychology');

INSERT INTO bookcategories (book_id, category_id) 
VALUES 
    (1, 1),  
    (1, 2),  
    (2, 2),  
    (3, 3);  
