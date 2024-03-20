--create database WatchStore2

USE [WatchStore2]
create table Categories
(
   ID [int] primary key,
   [name] nvarchar(30) ,
   describe nvarchar(50), 
)

select * from Categories
select max(id) as id from Categories
go
create table Account(
	aid int primary key,
	[username] nvarchar(100),
	[password] nvarchar(100),
	[role] int,
	[status] bit,
)
insert into Account values
(6,'lam','111',1,0),
(7,'anh','111',1,0),
(8,'gorgie','111',1,0),
(5,'lan','111',1,0),
(1,'khiem','111',1,1),
(2,'long','111',2,1),
(3,'linh','111',1,1),
(4,'thanh','111',1,1)

create table AccountInfo(
	aid int primary key references Account(aid),
	[fullname] nvarchar(100),
	[email] nvarchar(100),
	[phone] nvarchar(11),
	[address] nvarchar(100),
	CONSTRAINT AI_PhoneEmailID UNIQUE(email,phone)  
)
alter table AccountInfo add  age int
alter table AccountInfo add  gender bit
insert into AccountInfo values
(1,N'Vũ Đình Khiêm','khiemvu08@gmail.com','0912763561','03 Tien Hai St',20,1),
(2,N'Nguyễn Thanh Long','longnguyen23@gmail.com','0912763562','03 Phong Lan St',22,1),
(3,N'Nguyễn Chi Linh','linhchi25@gmail.com','0912763563','03 Thanh Ha St',18,0),
(4,N'Vũ Văn Thanh','thanhvu08@gmail.com','0912763564','03 Tien Khue St',22,1),
(5,N'Vũ Thi Lan ','thanhvu08@gmail.com','0912763565','03 Tien Khue St',21,0)

select * from Account a inner join AccountInfo ai on a.aid=ai.aid
create table AccountImage(
	aid int primary key references Account(aid),
	[image] varbinary(max)
)

go
insert into Categories values(1,'casio',N'gọn nhẹ, bền')
insert into Categories values(2,'fossil',N'dáng đẹp, nhiều chức năng')
insert into Categories values(3,'tissot',N'Hiện đại')
insert into Categories values(4,'rolex',N'Sang trọng')
create table products
(
       ID varchar(10)  primary key,
       [name] [nvarchar](max) NULL,
		[price] [money] NULL,
		[describe] [nvarchar](max) NULL,
		[image] [nvarchar](max) NULL,
       [cid] [int] references Categories(ID),
	   details nvarchar(max),
	  
)

GO
--tissot
INSERT products(ID,name,price,describe,image,cid,details)
VALUES
('8', N'Tissot Excellent', 600,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot excellent.png,img\Tissot\tissot excellent2.png,img\Tissot\tissot excellent3.png,img\Tissot\tissot excellent4.png','3','small,gold,classic,black'),
('9', N'Tissot Gold Bezel', 550,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot gold bezel.png,img\Tissot\tissot gold bezel2.png,img\Tissot\tissot gold bezel3.png,img\Tissot\tissot gold bezel4.png','3','small,gold,classic,none'),
('10', N'Tissot Powermatic', 700,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot powermatic.png,img\Tissot\tissot powermatic2.png,img\Tissot\tissot powermatic3.png,img\Tissot\tissot powermatic4.png','3','small,gold,classic,none'),
('11', N'Tissot Seastar', 800,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot seastar.png,img\Tissot\tissot seastar2.png,img\Tissot\tissot seastar3.png,img\Tissot\tissot seastar4.png','3','medium,platinum,classic,blue'),
('12', N'Tissot Supersport Chrono', 500,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot supersport chrono.png,img\Tissot\tissot supersport chrono2.png,img\Tissot\tissot supersport chrono3.png,img\Tissot\tissot supersport chrono4.png','3','small,leather,classic,black'),
('13', N'Tissot T-race Cycling', 500,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot t-race cycling.png,img\Tissot\tissot t-race cycling2.png,img\Tissot\tissot t-race cycling3.png,img\Tissot\tissot t-race cycling4.png','3','small,leather,classic,black'),
('4', N'tissot gentlement',550 ,N'This Memphis design is a thrilling throwback to a pop of culture phenomenon. The Tissot Heritage Memphis stands out from the crowd with a seconds indicator in the center that dispenses with a hand in favor of a small disc marked with a dot. The case backs comprise a pattern of seemingly random geometric shapes, reminiscent of the backgrounds of all Memphis décor. This pattern is repeated on the interior faces of the rubber straps. Show your true colors through a geometric and vivid design.',N'img\Tissot\tissot gentlement.png,img\Tissot\tissot gentlement4.png,img\Tissot\tissot gentlement2.png,img\Tissot\tissot gentlement3.png','3','medium,platinum,classic,blue'),
('14', N'Tissot telemeter', 500,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot memphis gent.png,
img\Tissot\tissot memphis gent2.png,img\Tissot\tissot memphis gent3.png,img\Tissot\tissot memphis gent4.png','3','small,leather,classic,yellow'),
('15', N'Tissot telemeter', 500,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Tissot\tissot telemeter.png,
img\Tissot\tissot telemeter2.png,img\Tissot\tissot telemeter3.png,img\Tissot\tissot telemeter4.png','3','small,leather,classic,black')

--casio
INSERT products(ID,name,price,describe,image,cid,details)
VALUES
('5', N'Casio-MRG', 50,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',N'img\Casio\g-shock mrg.png,img\Casio\g-shock mrg2.png,img\Casio\g-shock mrg3.png,img\Casio\g-shock mrg4.png','1','big,silicone,electronics,black,red'),
('6', N'Casio Master Of Style', 20,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',
N'img\Casio\masterofg.png,img\Casio\masterofg2.png,img\Casio\masterofg3.png,img\Casio\masterofg4.png','1','big,silicone,electronics,black,orange'),
('7', N'Casio MudMaster', 30,N'Sợi carbon gia cố là vật liệu tổng hợp được sử dụng trong thân máy bay. Mẫu đồng hồ sử dụng vật liệu này để tạo nên các cặp bộ phận đặc biệt ở vị trí 12 và 6 giờ. Sợi carbon và nhựa epoxy phát quang sẽ trải qua quá trình đúc và cắt kim loại ở nhiệt độ cao, áp suất cao, sau đó được đánh bóng và sơn. Khi có ánh sáng, chúng vẫn giữ được vẻ ngoài màu đen, nhưng trong bóng tối, chúng sẽ phát sáng với các họa tiết tia sáng mặt trời khớp với đường gờ.
*Màu sắc phát quang chính xác sẽ thay đổi theo từng chiếc đồng hồ do các chi tiết trong quy trình sản xuất.',
N'img\Casio\mudmaster.png,img\Casio\mudmaster2.png,img\Casio\mudmaster3.png,img\Casio\mudmaster4.png','1','big,silicone,electronics,green'),
('16', N'Casio Frogman Style', 40,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',
N'img\Casio\frogman.png,img\Casio\frogman2.png,img\Casio\frogman3.png,img\Casio\frogman4.png','1','big,silicone,electronics,red,black'),
('17', N'Casio Jungle Style', 40,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',
N'img\Casio\jungle.png,img\Casio\jungle2.png,img\Casio\jungle3.png,img\Casio\jungle4.png','1','big,silicone,electronics,blue,red'),
('18', N'Casio Rainbow Style', 30,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',
N'img\Casio\rainbow.png,img\Casio\rainbow2.png,img\Casio\rainbow3.png,img\Casio\rainbow4.png','1','big,silicone,electronics,black'),
('19', N'Casio Sunshine Style', 20,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',
N'img\Casio\sunshine.png,img\Casio\sunshine2.png,img\Casio\sunshine3.png,img\Casio\sunshine4.png','1','big,silicone,electronics,yellow'),
('20', N'Casio Red Frogman Style', 50,N'Đếm bước bằng cảm biến gia tốc 3 trục: Khoảng hiển thị từ 0 đến 999.999 bước Tiết kiệm pin: Cảm biến tự động chuyển sang trạng thái nghỉ sau một thời gian không hoạt động nhất định
Cao độ kế Khoảng đo: -700 đến 10.000 m (-2.300 đến 32.800 ft.) Đơn vị đo: 1 m (5 ft.) Biểu đồ chỉ báo kim về chênh lệch độ cao Kết quả đo ghi nhớ thủ công (lên đến 14 bản ghi, mỗi bản ghi bao gồm độ cao, ngày tháng, giờ) Tự động ghi dữ liệu (Cao độ cao/thấp, tự động tăng và giảm tích lũy) Khác: Chỉ số độ cao tương đối (±100 m /±1000 m), khoảng thời gian đo có thể lựa chọn: 5 giây hoặc 2 phút (1 giây chỉ trong 3 phút đầu tiên) *Chuyển đổi giữa mét (m) và feet (ft)
Khí áp kế Khoảng đo: 260 đến 1.100 hPa (7,65 đến 32,45 inHg) Đơn vị đo: 1 hPa (0,05 inHg) Kim chỉ báo chênh lệch áp suất (±10 hPa) Đồ thị xu hướng áp suất khí quyển (trong 20 giờ hoặc 56 giờ qua) Báo giờ thông tin xu hướng áp suất khí áp kế (tiếng bíp và mũi tên cho biết những thay đổi áp suất quan trọng) Đồ thị xu hướng áp suất khí quyển có thể hiển thị thông tin đo lường trong 20 phút hoặc 56 phút vừa qua. Chỉ báo mức chênh lệch áp suất của kim đồng hồ sẽ hiển thị giá trị theo đơn vị ±0,1 hPa. *Chuyển đổi giữa hPa và inHg
La bàn số Đo và hiển thị hướng dưới dạng một trong 16 điểm Khoảng đo: 0 to 359° Đơn vị đo: 1° đo liên tục trong 60 giây Chỉ báo kim hướng bắc Điều chỉnh độ lệch từ Hiệu chỉnh hướng (hiệu chỉnh 2 điểm, hiệu chỉnh theo hình số 8, hiệu chỉnh tự động)
Nhiệt kế Khoảng đo: -10 đến 60℃ (14 đến 140℉) Đơn vị đo: 0,1℃ (0.2℉) *Chuyển đổi giữa độ C (℃) và độ F (℉)',
N'img\Casio\red frogman.png,img\Casio\red frogman2.png,img\Casio\red frogman3.png,img\Casio\red frogman4.png','1','small,silicone,electronics,red'),
('1', N'G-shock Black', 50,N'Sợi carbon gia cố là vật liệu tổng hợp được sử dụng trong thân máy bay. Mẫu đồng hồ sử dụng vật liệu này để tạo nên các cặp bộ phận đặc biệt ở vị trí 12 và 6 giờ. Sợi carbon và nhựa epoxy phát quang sẽ trải qua quá trình đúc và cắt kim loại ở nhiệt độ cao, áp suất cao, sau đó được đánh bóng và sơn. Khi có ánh sáng, chúng vẫn giữ được vẻ ngoài màu đen, nhưng trong bóng tối, chúng sẽ phát sáng với các họa tiết tia sáng mặt trời khớp với đường gờ.
*Màu sắc phát quang chính xác sẽ thay đổi theo từng chiếc đồng hồ do các chi tiết trong quy trình sản xuất.',
N'img\Casio\g-shock black.png,img\Casio\g-shock black2.png,img\Casio\g-shock black3.png,img\Casio\g-shock black4.png','1','small,silicone,electronics,black')


--rolex
INSERT products(ID,name,price,describe,image,cid,details)
VALUES
('2', N'Rolex DAYTONA', 4500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\DAYTONA.png,img\Rolex\DAYTONA2.png,img\Rolex\DAYTONA3.png,img\Rolex\DAYTONA4.png','4','medium,platinum,classic,white'),
('3', N'Rolex EXPLORER', 5500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\EXPLORER.png,img\Rolex\EXPLORER2.png,img\Rolex\EXPLORER3.png,img\Rolex\EXPLORER4.png','4','medium,platinum,classic,yellow'),
('21', N'Rolex GMT-WATERFALL', 6500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\GMT-WATERFALL.png,img\Rolex\GMT-WATERFALL2.png,img\Rolex\GMT-WATERFALL3.png,img\Rolex\GMT-WATERFALL4.png','4','medium,platinum,classic,red,blue'),
('22', N'Rolex SEA-DWELLER', 5500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\SEA-DWELLER.png,img\Rolex\SEA-DWELLER2.png,img\Rolex\SEA-DWELLER3.png,img\Rolex\SEA-DWELLER4.png','4','medium,gold,classic,white'),
('23', N'Rolex SUBMARINE', 5500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\SUBMARINE.png,img\Rolex\SUBMARINE2.png,img\Rolex\SUBMARINE3.png,img\Rolex\SUBMARINE4.png','4','medium,platinum,classic,white'),
('24', N'Rolex YATCH-MASTER', 3500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\YATCH-MASTER.png,img\Rolex\YATCH-MASTER2.png,img\Rolex\YATCH-MASTER3.png,img\Rolex\YATCH-MASTER4.png','4','medium,platinum,classic,blue'),
('33', N'Rolex 1908 Classic', 3500,N'Tất cả đồng hồ Rolex được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của Rolex. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ Rolex chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của Rolex, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ Rolex chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ Rolex sẵn có.',
N'img\Rolex\1908 Classic.png,img\Rolex\1908 Classic2.png,img\Rolex\1908 Classic3.png,img\Rolex\1908 Classic4.png','4','small,gold,classic,none')


--fossil
INSERT products(ID,name,price,describe,image,cid,details)
VALUES
('25', N'Fossil Everette', 100,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\Everette.png,img\fossil\Everette2.png,img\fossil\Everette3.png,img\fossil\Everette4.png','2','medium,platinum,classic,white'),
('26', N'Fossil Heritage HP', 300,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\Heritage HP.png,img\fossil\Heritage HP2.png,img\fossil\Heritage HP3.png,img\fossil\Heritage HP4.png','2','medium,leather,classic,yellow'),
('27', N'Fossil INSTRIPTIONS', 150,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\INSTRIPTIONS.png,img\fossil\INSTRIPTIONS2.png,img\fossil\INSTRIPTIONS3.png,img\fossil\INSTRIPTIONS4.png','2','medium,platinum,classic,orange'),
('28', N'Fossil Jungle', 350,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\Jungle.png,img\fossil\Jungle2.png,img\fossil\Jungle3.png,img\fossil\Jungle4.png','2','medium,leather,classic,black'),
('29', N'Fossil MINALIST', 350,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\MINALIST.png,img\fossil\MINALIST2.png,img\fossil\MINALIST3.png,img\fossil\MINALIST4.png','2','medium,leather,classic,black'),
('30', N'Fossil NEUTRA', 350,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\Neutra.png,img\fossil\Neutra2.png,img\fossil\Neutra3.png,img\fossil\Neutra4.png','2','small,leather,classic,black'),
('31', N'Fossil NEUTRUAL GOLD', 450,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\NEUTRUAL.png,img\fossil\NEUTRUAL2.png,img\fossil\NEUTRUAL3.png,img\fossil\NEUTRUAL4.png','2','small,leather,classic,black'),
('32', N'Fossil TOWNMAN', 350,N'Tất cả đồng hồ fossil được lắp ráp bằng tay cực kỳ cẩn thận để đảm bảo chất lượng vượt trội. Lẽ tất nhiên, tiêu chuẩn cao như vậy đã hạn chế sản lượng của chúng tôi. đôi khi, nhu cầu mua đồng hồ vượt quá mức năng suất tiêu chuẩn của fossil. 
Do đó, có thể hạn chế lượng có sẵn một số mẫu sản phẩm nhất định. Đồng hồ fossil chỉ được bán độc quyền tại các cửa hàng bán lẻ chính thức của fossil, các địa chỉ nhập hàng hóa đều đặn và quản lý độc lập quá trình phân bổ và bán đồng hồ cho khách hàng.
DAFC tự hào là một phần trong mạng lưới các nhà bán lẻ fossil chính thức trên toàn thế giới và có thể cung cấp thông tin về những chiếc đồng hồ fossil sẵn có.',
N'img\fossil\TOWNMAN.png,img\fossil\TOWNMAN2.png,img\fossil\TOWNMAN3.png,img\fossil\TOWNMAN4.png','2','medium,leather,classic,black')
create table Cart(
	aid int  references Account(aid),
	pid varchar(10) references products(ID),
	[number] int,
	primary key(aid,pid)
)
create table ProductTransaction(
	Tid int IDENTITY(1,1) PRIMARY KEY ,
	[money] money,
	[aid] int,
	)
	
create table TransactionDetail(
	Tid int references ProductTransaction(Tid),
	[pid] varchar(10) references products(ID),
	[number] int,
	primary key(Tid,pid)
)
alter table TransactionDetail add [date] date
create table ProductSale(
	pid varchar(10) primary key references products(ID),
	[number] int,
)

--select * from products p inner join Categories c on c.ID=p.cid where c.ID=1
--select * from products p inner join Categories c on c.ID=p.cid order by c.id desc,p.ID
--select * from Categories
--delete from products where cid=4
select * from products
