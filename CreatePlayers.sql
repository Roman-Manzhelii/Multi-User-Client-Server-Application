DROP TABLE IF EXISTS player;
CREATE TABLE player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    team VARCHAR(255),
    position VARCHAR(255)
);



INSERT INTO player (name, age, team, position) VALUES
('John Doe', 24, 'Team A', 'Forward'),
('Jane Doe', 22, 'Team B', 'Midfielder'),
('Mike Smith', 27, 'Team C', 'Defender'),
('Sarah Johnson', 21, 'Team D', 'Goalkeeper'),
('Alex Brown', 23, 'Team E', 'Forward'),
('Linda White', 25, 'Team F', 'Midfielder'),
('James Davis', 20, 'Team G', 'Defender'),
('Patricia Wilson', 26, 'Team H', 'Goalkeeper'),
('Robert Moore', 29, 'Team I', 'Forward'),
('Jennifer Taylor', 28, 'Team J', 'Midfielder');
