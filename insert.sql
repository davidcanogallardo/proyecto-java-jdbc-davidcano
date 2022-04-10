--------------------------------------------- clients ---------------------------------------------
-- 1
INSERT INTO client 
(dni,name,lastname,birthdate,email,address,phone) VALUES 
('66665893W','Hisoka','Morow','1995-03-04','hisoka@gmail.com',ARRAY ['Los Bustos','Zaragoza','09136','Avinguda Manuel 672 Bajo 8º'],ARRAY ['123456789']),
-- 2
('59745500X','Haruhi','Suzumiya','1996-03-16','haruhi@sos.jp',ARRAY ['Las Bañuelos de Ulla','Illes Balears','02687', 'Avenida Lovato 317 5º 6º'],ARRAY ['673362323']),
-- 3
('83587029S','Yui','Hirasawa','1998-06-06','yui@htt.jp',ARRAY ['Brito de las Torres', 'Cantabria','24137','Camino Guillermo 9 9º B'],ARRAY ['673262323']),
-- 3
('71236229P','Madara','Uchiha','1996-06-06','madara@gmail.com',ARRAY ['As Salvador','Pontevedra','32948','Travessera Ismael 5 Entre suelo 2º'],ARRAY ['673362323']),
-- 4
('11970041J','Isaac','Netero','1997-06-06','isaac@gmail.com',ARRAY ['Escribano Alta','Sevilla','72481','Camiño Reina 056 08º 6º'] ,ARRAY ['679862323']),
-- 5
('14641343A','Shirou','Emiya','1999-06-06','shirou@swords.com',ARRAY ['Vall Crespo del Bages','Tarragona','16732','Camino Yaiza 8 5º A'] ,ARRAY ['643367323']),
-- 6
('47495332X','Rika','Furude','2000-06-06','rika@nipah.com',ARRAY ['Cantú Alta','Burgos','01867','Passeig Gael 1 Bajo 5º'] ,ARRAY ['613362323']),
-- 7
('85194743Y','Rika2','Furude2','2002-06-06','rika2@nipah.com',ARRAY ['Las Aguilar','Navarra','98740','Avinguda Domínquez 841 Bajo 4º'],ARRAY ['673362328']),
-- 8
('85194743Y','Gintoki','Sakata','2003-06-06','gintoki@gmail.com',ARRAY ['La Castellanos de las Torres','Tarragona','58150','Camino Solís 21 Ático 1º'] ,ARRAY ['673962323']),
-- 9
('85194743Y','Killua','Zoldyck','2004-06-06','killua@gmail.com',ARRAY ['Ponce de San Pedro','Valencia','83351','Rúa Andrés 0 02º C'] ,ARRAY ['671302323']),
-- 10
('85194743Y','Kaguya','Shinomiya','1996-06-06','kaguya@gmail.com',ARRAY ['Pons de la Sierra','Alicante','04848','Camino Alejandro 78 Bajos'] ,ARRAY ['670362323']);
--------------------------------------------- presence ---------------------------------------------
-- 1
INSERT INTO presence 
(id,date_enter,date_leave) VALUES
-- 2
(1,'2022-11-02 15:17:00','2022-11-02 23:11:00'),
-- 3
(2,'2022-11-25 14:46:00','2022-11-25 17:33:00'),
-- 4
(3,'2022-10-18 11:27:00','2022-10-18 16:52:00'),
-- 5
(2,'2022-09-14 12:49:00','2022-09-14 16:52:00'),
-- 6
(5,'2022-09-14 12:49:00','2022-09-14 16:52:00'),
-- 7
(7,'2022-11-25 14:03:00','2022-11-25 14:59:00'),
        -- 8
(9,'2022-04-07 12:47:00','2022-04-07 13:55:00'),
-- 9
(1,'2022-04-05 16:20:00','2022-04-05 21:00:00'),
-- 10
(2,'2022-12-09 14:03:00','2022-12-09 20:30:00');

--------------------------------------------- PACK ---------------------------------------------
-- 1
INSERT INTO pack 
(name,price,stock,date_start,date_end,discount) VALUES
('Frutas Zoan',100.12,1,'2022-01-03','2022-03-01',10),
-- 2
('Frutas Logia',5379,1,'2022-02-03','2022-05-03',0),
-- 3
('Frutas Paramecia',200,1,'2021-09-23','2022-02-04',6),
-- 4
('Frutas famosas',2745,1,'2022-01-18','2022-02-18',0),
-- 5
('Frutas de yonkous',7642,1,'2022-01-04','2022-03-23',0),
-- 6
('Frutas de cada tipo',125,1,'2022-02-04','2022-03-10',67),
-- 7
('Frutas de shichibukais',1742,1,'2022-01-25','2022-03-25',0),
-- 8
('Frutas de marines',574,1,'2022-02-17','2022-03-31',0),
-- 9
('Frutas Zoan mitologicas',5235,1,'2022-03-01','2022-03-28',0),
-- 10
('Frutas smile',45,1,'2022-02-08','2022-03-24',0);

--------------------------------------------- provider ---------------------------------------------
-- 1
INSERT INTO provider 
(dni,name,lastname,birthdate,email,address,phone) VALUES 
('76486423S','Zoro','Roronoa','2000-08-09','zoro@gmail.com',ARRAY ['Hidalgo de Ulla','Guadalajara','17279','Travesía Paola 610 Bajos'] ,ARRAY ['670362313']),
-- 2
('88546101B','Hiluluk','','2000-04-05','hiluluk@gmail.com',ARRAY ['Villa Granado del Barco','Lleida','67279','Calle Olivares 982 9º 8º'] ,ARRAY ['610362323']),
-- 3
('30818777L','Kinemon','Foxfire','2002-04-21','kinemon@gmail.com',ARRAY ['As Carrera,A Coruña','18126','Passeig Alicia 97 99º D'] ,ARRAY ['670312323']),
-- 4
('38854476R','Rikka','Takarada','2004-11-24','rikka@gmail.com',ARRAY ['Corral del Puerto','Alicante','38979','Travesía Urrutia 95 13º C'] ,ARRAY ['670361323']),
-- 5
('80983991W','Eren','Yeager','2004-07-07','eren@freedom.com',ARRAY ['La Montaño','Teruel','78914','Calle Jon 6 70º C'] ,ARRAY ['670362373']),
-- 6
('87347317X','Naruto','Uzumaki','1999-11-16','norauto@gmail.com',ARRAY ['A Valles','Salamanca','38069','Paseo Valeria 980 0º E'] ,ARRAY ['670362329']),
-- 7
('28437345F','Akane','Tsunemori','1998-11-27','akane@gmail.com',ARRAY ['San Rendón de Lemos','Cantabria','83544','Passeig María 325 Ático 4º'] ,ARRAY ['670362313']),
-- 8
('49508076R','Gabriela','Méndez Tercero','1997-05-22','gabriela@gmail.com',ARRAY ['Os Valentín','Murcia','32705','Plaça Vera 53 06º E'] ,ARRAY ['670352323']),
-- 9
('49294596F','Dario','Vicente','1997-01-15','dario@gmail.com',ARRAY ['La Delapaz de Lemos','Cádiz','61981','Plaza Baca 5 Bajos'] ,ARRAY ['650362323']),
-- 10
('46763602W','Pablo','Abrego','1996-11-14','pablo@gmail.com',ARRAY ['Martos de Ulla','Cantabria','96384','Calle Rolón 97 8º 7º'] ,ARRAY ['670362328']);

--------------------------------------------- PRODUCTO ---------------------------------------------
-- 1
INSERT INTO product
(id,name,price,stock,date_start,date_end) VALUES
(1,'Mera Mera no Mi',678,12,'2022-01-25','2022-03-25'),
-- 2
(2,'Gomu Gomu no Mi',4553,1,'2022-02-08','2022-03-24'),
-- 3
(3,'Ope Ope no Mi',1254,54,'2022-04-12','2022-05-12'),
-- 4
(4,'Hito Hito no Mi',34,321,'2022-03-30','2022-05-05'),
-- 5
(5,'Tori Tori no Mi Fenix',432,23,'2022-04-05','2022-05-13'),
-- 6
(6,'Gura Gura no Mi',2432,23,'2022-04-07','2022-05-09'),
-- 7
(7,'Shanks Shanks no Mi',9999,1,'2022-03-28','2022-05-06'),
-- 8
(8,'Smile Nezumi',5,561,'2022-03-23','2022-05-20'),
-- 9
(9,'Neko Neko no Mi',435,47,'2022-04-15','2022-04-25'),
-- 10
(10,'Ito Ito no Mi',1435,3,'2022-04-25','2022-05-13');

--------------------------------------------- PRODUCTOs de pack ---------------------------------------------
INSERT INTO pack_products VALUES
(1,9),
(2,1),
(3,2),
(3,10),
(3,6),
(4,3),
(5,6),
(5,7),
(6,1),
(6,2),
(6,9);