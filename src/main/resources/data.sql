

INSERT INTO `category` (`created_at`, `updated_at`, `label`) VALUES
('2025-12-22 23:36:52.000000', '2025-12-22 23:36:52.000000', 'gaming'),
('2025-12-22 23:36:52.000000', '2025-12-22 23:36:52.000000', 'clothing'),
('2025-12-03 17:46:45.000000', '2025-12-22 23:36:52.000000', 'home'),
('2025-12-02 17:47:37.000000', '2025-12-03 17:47:37.000000', 'electronics'),
('2025-12-22 23:36:52.000000', '2025-12-22 23:36:52.000000', 'sports');


INSERT INTO product (name, description, image_url, is_active, price, stock, discount, rating, categorie_id, created_at, updated_at)
VALUES
(
  'Clavier mécanique',
  'Clavier mécanique rétroéclairé RGB avec switches bleus',
  'https://example.com/images/clavier.jpg',
  true,
  79.99,
  12,
  0.0,
  4.9,
  4,
  NOW(),
  NOW()
),
(
  'Souris gamer',
  'Souris optique ergonomique 16000 DPI avec rétroéclairage personnalisable',
  'https://example.com/images/souris.jpg',
  true,
  59.99,
  25,
  0.0,
  4.5,
  1,
  NOW(),
  NOW()
),
(
  'Casque audio',
  'Casque circum-aural avec micro amovible et son surround 7.1',
  'https://example.com/images/casque.jpg',
  true,
  99.99,
  8,
  0.0,
  2.6,
  5,
  NOW(),
  NOW()
),
(
  'Écran 27 pouces',
  'Écran gaming 27 pouces QHD 165Hz avec technologie G-Sync',
  'https://example.com/images/ecran.jpg',
  true,
  199.99,
  5,
  0.0,
  2.0,
  4,
  NOW(),
  NOW()
);