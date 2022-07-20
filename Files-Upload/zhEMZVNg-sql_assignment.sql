/* I. CREATE TABLES */

-- faculty (Khoa trong trường)
create table faculty (
	id number primary key,
	name nvarchar2(30) not null
);

-- subject (Môn học)
create table subject(
	id number primary key,
	name nvarchar2(100) not null,
	lesson_quantity number(2,0) not null -- tổng số tiết học
);

-- student (Sinh viên)
create table student (
	id number primary key,
	name nvarchar2(30) not null,
	gender nvarchar2(10) not null, -- giới tính
	birthday date not null,
	hometown nvarchar2(100) not null, -- quê quán
	scholarship number, -- học bổng
	faculty_id number not null constraint faculty_id references faculty(id) -- mã khoa
);

-- exam management (Bảng điểm)
create table exam_management(
	id number primary key,
	student_id number not null constraint student_id references student(id),
	subject_id number not null constraint subject_id references subject(id),
	number_of_exam_taking number not null, -- số lần thi (thi trên 1 lần được gọi là thi lại) 
	mark number(4,2) not null -- điểm
);



/*================================================*/

/* II. INSERT SAMPLE DATA */

-- subject
insert into subject (id, name, lesson_quantity) values (1, n'Cơ sở dữ liệu', 45);
insert into subject values (2, n'Trí tuệ nhân tạo', 45);
insert into subject values (3, n'Truyền tin', 45);
insert into subject values (4, n'Đồ họa', 60);
insert into subject values (5, n'Văn phạm', 45);


-- faculty
insert into faculty values (1, n'Anh - Văn');
insert into faculty values (2, n'Tin học');
insert into faculty values (3, n'Triết học');
insert into faculty values (4, n'Vật lý');


-- student
insert into student values (1, n'Nguyễn Thị Hải', n'Nữ', to_date('19900223', 'YYYYMMDD'), 'Hà Nội', 130000, 2);
insert into student values (2, n'Trần Văn Chính', n'Nam', to_date('19921224', 'YYYYMMDD'), 'Bình Định', 150000, 4);
insert into student values (3, n'Lê Thu Yến', n'Nữ', to_date('19900221', 'YYYYMMDD'), 'TP HCM', 150000, 2);
insert into student values (4, n'Lê Hải Yến', n'Nữ', to_date('19900221', 'YYYYMMDD'), 'TP HCM', 170000, 2);
insert into student values (5, n'Trần Anh Tuấn', n'Nam', to_date('19901220', 'YYYYMMDD'), 'Hà Nội', 180000, 1);
insert into student values (6, n'Trần Thanh Mai', n'Nữ', to_date('19910812', 'YYYYMMDD'), 'Hải Phòng', null, 3);
insert into student values (7, n'Trần Thị Thu Thủy', n'Nữ', to_date('19910102', 'YYYYMMDD'), 'Hải Phòng', 10000, 1);


-- exam_management
insert into exam_management values (1, 1, 1, 1, 3);
insert into exam_management values (2, 1, 1, 2, 6);
insert into exam_management values (3, 1, 2, 2, 6);
insert into exam_management values (4, 1, 3, 1, 5);
insert into exam_management values (5, 2, 1, 1, 4.5);
insert into exam_management values (6, 2, 1, 2, 7);
insert into exam_management values (7, 2, 3, 1, 10);
insert into exam_management values (8, 2, 5, 1, 9);
insert into exam_management values (9, 3, 1, 1, 2);
insert into exam_management values (10, 3, 1, 2, 5);
insert into exam_management values (11, 3, 3, 1, 2.5);
insert into exam_management values (12, 3, 3, 2, 4);
insert into exam_management values (13, 4, 5, 2, 10);
insert into exam_management values (14, 5, 1, 1, 7);
insert into exam_management values (15, 5, 3, 1, 2.5);
insert into exam_management values (16, 5, 3, 2, 5);
insert into exam_management values (17, 6, 2, 1, 6);
insert into exam_management values (18, 6, 4, 1, 10);



/*================================================*/

/* III. QUERY */


 /********* A. BASIC QUERY *********/

-- 1. Liệt kê danh sách sinh viên sắp xếp theo thứ tự:
--      a. id tăng dần
--      b. giới tính
--      c. ngày sinh TĂNG DẦN và học bổng GIẢM DẦN


-- 2. Môn học có tên bắt đầu bằng chữ 'T'

-- 3. Sinh viên có chữ cái cuối cùng trong tên là 'i'

-- 4. Những khoa có ký tự thứ hai của tên khoa có chứa chữ 'n'

-- 5. Sinh viên trong tên có từ 'Thị'

-- 6. Sinh viên có ký tự đầu tiên của tên nằm trong khoảng từ 'a' đến 'm', sắp xếp theo họ tên sinh viên

-- 7. Sinh viên có học bổng lớn hơn 100000, sắp xếp theo mã khoa giảm dần

-- 8. Sinh viên có học bổng từ 150000 trở lên và sinh ở Hà Nội

-- 9. Những sinh viên có ngày sinh từ ngày 01/01/1991 đến ngày 05/06/1992

-- 10. Những sinh viên có học bổng từ 80000 đến 150000

-- 11. Những môn học có số tiết lớn hơn 30 và nhỏ hơn 45



-------------------------------------------------------------------

/********* B. CALCULATION QUERY *********/

-- 1. Cho biết thông tin về mức học bổng của các sinh viên, gồm: Mã sinh viên, Giới tính, Mã 
		-- khoa, Mức học bổng. Trong đó, mức học bổng sẽ hiển thị là “Học bổng cao” nếu giá trị 
		-- của học bổng lớn hơn 500,000 và ngược lại hiển thị là “Mức trung bình”.
		
-- 2. Tính tổng số sinh viên của toàn trường

-- 3. Tính tổng số sinh viên nam và tổng số sinh viên nữ.

-- 4. Tính tổng số sinh viên từng khoa

-- 5. Tính tổng số sinh viên của từng môn học

-- 6. Tính số lượng môn học mà sinh viên đã học

-- 7. Tổng số học bổng của mỗi khoa	

-- 8. Cho biết học bổng cao nhất của mỗi khoa

-- 9. Cho biết tổng số sinh viên nam và tổng số sinh viên nữ của mỗi khoa

-- 10. Cho biết số lượng sinh viên theo từng độ tuổi

-- 11. Cho biết những nơi nào có ít nhất 2 sinh viên đang theo học tại trường

-- 12. Cho biết những sinh viên thi lại ít nhất 2 lần

-- 13. Cho biết những sinh viên nam có điểm trung bình lần 1 trên 7.0 

-- 14. Cho biết danh sách các sinh viên rớt ít nhất 2 môn ở lần thi 1 (rớt môn là điểm thi của môn không quá 4 điểm)

-- 15. Cho biết danh sách những khoa có nhiều hơn 2 sinh viên nam

-- 16. Cho biết những khoa có 2 sinh viên đạt học bổng từ 200000 đến 300000

-- 17. Cho biết sinh viên nào có học bổng cao nhất


-------------------------------------------------------------------

/********* C. DATE/TIME QUERY *********/

-- 1. Sinh viên có nơi sinh ở Hà Nội và sinh vào tháng 02

-- 2. Sinh viên có tuổi lớn hơn 20

-- 3. Sinh viên sinh vào mùa xuân năm 1990



-------------------------------------------------------------------


/********* D. JOIN QUERY *********/

-- 1. Danh sách các sinh viên của khoa ANH VĂN và khoa VẬT LÝ

-- 2. Những sinh viên nam của khoa ANH VĂN và khoa TIN HỌC

-- 3. Cho biết sinh viên nào có điểm thi lần 1 môn cơ sở dữ liệu cao nhất

-- 4. Cho biết sinh viên khoa anh văn có tuổi lớn nhất.

-- 5. Cho biết khoa nào có đông sinh viên nhất

-- 6. Cho biết khoa nào có đông nữ nhất

-- 7. Cho biết những sinh viên đạt điểm cao nhất trong từng môn

-- 8. Cho biết những khoa không có sinh viên học

-- 9. Cho biết sinh viên chưa thi môn cơ sở dữ liệu

-- 10. Cho biết sinh viên nào không thi lần 1 mà có dự thi lần 2


