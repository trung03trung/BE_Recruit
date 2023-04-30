-- Level (fresher, junior, senior, master, ... ): là 1 trong các giá trị được cấu hình trong DB
Create table rank(
             id  NUMBER(4) primary key NOT NULL,
             code nvarchar2(50) NOT NULL,
             description nvarchar2(50),
             is_delete INTEGER NOT NULL
);

-- status_job (Trạng thái job: ch�? xét duyệt, đã từ chối, đang tuyển..)
Create table status_job(
            id NUMBER(4) primary key,
            code nvarchar2(50),
            description nvarchar2(50),
            is_delete INTEGER NOT NULL
);
-- trạng thái(đang ph�?ng vấn, đã tuyển)
Create table status_job_register(
            id NUMBER(4) primary key,
            code nvarchar2(50),
            description nvarchar2(50),
            is_delete INTEGER NOT NULL
);

-- academic level (trình độ h�?c vấn)----
Create table academic_level(
            id NUMBER(4) primary key,
            code nvarchar2(50),         -- trình độ h�?c vấn---
            description nvarchar2(50),
            is_delete INTEGER NOT NULL
);
-- Hình thức làm việc (full time, part time, freelancer, intern, ... ): là 1 trong các giá trị được cấu hình trong DB
CREATE TABLE working_form(
            id NUMBER NOT NULL,
            code VARCHAR(50) NOT NULL,
            description  VARCHAR(100) NOT NULL,
            is_delete INTEGER NOT NULL,
            PRIMARY KEY(id )
);
-- thông tin cá nhân
CREATE TABLE users(
            id NUMBER(4) NOT NULL  PRIMARY KEY,
            name VARCHAR(50) NOT NULL,
            user_name VARCHAR(20) NOT NULL UNIQUE,
            email VARCHAR(50) NOT NULL UNIQUE ,
            password VARCHAR(60) NOT NULL,
            phone_number VARCHAR(20) NOT NULL UNIQUE,
            home_town VARCHAR(100) ,
            gender VARCHAR(100) ,
            birth_day DATE NOT NULL ,
            avatar VARCHAR(100),
            activate INTEGER NOT NULL,
            is_delete INTEGER NOT NULL
);

Create table otp(
            id NUMBER(4) primary key,
            code varchar(20) NOT NULL,
            issue_at DATE NOT NULL,
            user_id  NUMBER(4)  NOT NULL,
            CONSTRAINT fk_user_otp FOREIGN KEY(user_id) REFERENCES users(id)
);
----file (Thông tin v�? kinh nghiệm, Thông tin v�? mong muốn)----
Create table profiles(
            user_id  NUMBER(4) PRIMARY KEY,
            skill varchar(50),                     -- Kĩ năng (là 1 tập các kĩ năng, ví dụ: Java, SQL, Docker, ….)
            number_years_experience NUMBER(4),     -- số năm kinh nghiệm
            academic_name_id number(4),            -- academic level (trình độ h�?c vấn)
            desired_salary varchar(50),            -- mức lương mong muốn
            desired_working_address varchar(50),   -- địa chỉ làm việc mong muốn
            desired_working_form varchar(50),      -- hình thức làm việc mong muốn
            is_delete INTEGER NOT NULL,
            CONSTRAINT fk_user_profile    FOREIGN KEY (user_id)    REFERENCES users (id),
            CONSTRAINT fk_academic_level    FOREIGN KEY (academic_name_id)    REFERENCES academic_level(id)
);

CREATE TABLE roles(
            id NUMBER(4) NOT NULL,
            code VARCHAR(20) NOT NULL UNIQUE,
            description VARCHAR(50) NOT NULL,
            is_delete INTEGER NOT NULL,
            PRIMARY KEY(id)
);

CREATE TABLE permisstion(
            user_id NUMBER(4) NOT NULL,
            role_id NUMBER(4) NOT NULL,
            PRIMARY KEY(user_id, role_id),
            CONSTRAINT fk_user    FOREIGN KEY (user_id)    REFERENCES users (id),
            CONSTRAINT fk_role    FOREIGN KEY (role_id)    REFERENCES roles(id)
);

Create table job_position(
             id NUMBER(4) primary key,
             code nvarchar2(50),
             description nvarchar2(50),
             is_delete INTEGER NOT NULL
);

CREATE TABLE job(
            id NUMBER(4) PRIMARY KEY,
            name VARCHAR(150) NOT NULL,
            job_position_id NUMBER(4) NOT NULL,       -- id vị trí công việc
            number_experience VARCHAR(100) NOT NULL,  -- số năm kinh nghiệm
            working_form_id  NUMBER(4) NOT NULL,      -- hình thức làm việc
            address_work VARCHAR(100) NOT NULL,
            academic_level_id NUMBER(4) NOT NULL,     -- trình độ h�?c vấn
            rank_id NUMBER(4) NOT NULL,               -- level id--
            qty_person INTEGER NOT NULL,              -- số lượng ngư�?i tuyển
            start_recruitment_date DATE NOT NULL,     -- ngày bắt đầu tuyển dụng
            due_date DATE NOT NULL,                   -- ngày kết thúc
            skills VARCHAR(100) NOT NULL,             -- kỹ năng
            description VARCHAR(2000) NOT NULL,
            interest VARCHAR(2000) NOT NULL,          -- quy�?n lợi
            job_requirement VARCHAR(2000) NOT NULL,   -- yêu cầu công việc
            salary_max INTEGER NOT NULL,              -- mức lương tối đa
            salary_min INTEGER NOT NULL,              -- mức lương nh�? nhất
            contact_id NUMBER(4) NOT NULL,            -- liên hệ
            create_id  NUMBER(4) NOT NULL,
            create_date DATE NOT NULL,
            update_id  NUMBER(4) NOT NULL,
            update_date DATE NOT NULL,                -- th�?i gian update mới nhất
            status_id NUMBER(4) NOT NULL,             -- trạng thái
            views  INTEGER ,
            is_delete INTEGER NOT NULL,
            CONSTRAINT fk_working_form FOREIGN KEY (working_form_id)  REFERENCES working_form(id),
            CONSTRAINT fk_academic_level_job FOREIGN KEY (academic_level_id)  REFERENCES academic_level (id),
            CONSTRAINT fk_rank FOREIGN KEY (rank_id)  REFERENCES rank (id),
            CONSTRAINT fk_contact FOREIGN KEY (contact_id)  REFERENCES users (id),
            CONSTRAINT fk_update FOREIGN KEY (update_id)  REFERENCES users (id),
            CONSTRAINT fk_create FOREIGN KEY (create_id)  REFERENCES users (id),
            CONSTRAINT fk_status_job FOREIGN KEY (status_id)  REFERENCES status_job(id),
            CONSTRAINT FK_JOB_POSITION FOREIGN KEY (job_position_id) REFERENCES JOB_POSITION(id)
);

--Chi tiết hồ sơ ứng tuyển
CREATE TABLE jobs_register(
            job_id NUMBER NOT NULL,
            user_id NUMBER NOT NULL,
            date_register DATE NOT NULL ,     -- �?ặt lịch ph�?ng vấn
            date_interview DATE,              -- ngày ph�?ng vấn
            method_interview VARCHAR(50),     -- Hình thức ph�?ng vấn: online, offline
            address_interview VARCHAR(50),
            status_id NUMBER NOT NULL,
            reason VARCHAR(50),               -- Lý do (chỉ có giá trị khi trạng thái là “Ứng viên bị từ chối�? hoặc “Ứng viên đã hủy ứng tuyển�?)
            cv_file VARCHAR(50) NOT NULL,
            media_type VARCHAR(50)NOT NULL,
            is_delete INTEGER NOT NULL,
            PRIMARY KEY(job_id, user_id ),

            CONSTRAINT fk_job_register  FOREIGN KEY (job_id)   REFERENCES job(id),
            CONSTRAINT fk_user_register   FOREIGN KEY (user_id)    REFERENCES users (id),
            CONSTRAINT fk_reg_status FOREIGN KEY (status_id)  REFERENCES status_job_register(id)
);

Create table type_notifications(
             id NUMBER(4) primary key,
             code nvarchar2(50),
             description nvarchar2(50),
             is_delete INTEGER NOT NULL
             )

Create table job_position(
            id NUMBER(4) primary key,
            code nvarchar2(50),
            description nvarchar2(50),
            is_delete INTEGER NOT NULL
);
-- thông báo
CREATE TABLE notifications(
            id NUMBER NOT NULL,
            sender_id  NUMBER NOT NULL,
            receiver_id  NUMBER NOT NULL,
            create_date DATE NOT NULL,
            content  varchar(200) NOT NULL,
            res_id NUMBER NOT NULL,           -- là job_id hoặc là job_reg_id;
            type_id NUMBER NOT NULL,
            is_delete INTEGER NOT NULL,
            PRIMARY KEY(id ) ,
            CONSTRAINT fk_type FOREIGN KEY (type_id)  REFERENCES type_notifications (id),
            CONSTRAINT fk_sender_id  FOREIGN KEY (sender_id)   REFERENCES users (id),
            CONSTRAINT fk_receiver_id   FOREIGN KEY (receiver_id)    REFERENCES users (id)
);
-- thông tin công ty
CREATE TABLE company(
            id NUMBER NOT NULL,
            name  VARCHAR(200) NOT NULL,
            name_acronym VARCHAR(80) NOT NULL,  -- tên viết tắt
            email VARCHAR(100) NOT NULL,
            hotline VARCHAR(100) NOT NULL,
            date_incoporation DATE NOT NULL,    -- ngày thành lập công ty
            tax_code VARCHAR(100) NOT NULL,     -- mã số thuế
            tax_date DATE NOT NULL,             -- Ngày cấp mã số thuế
            tax_place VARCHAR(50) NOT NULL,     -- Nơi cấp mã số thuế
            head_office VARCHAR(50) NOT NULL,   -- trụ sở chính
            number_staff INTEGER NOT NULL,      -- số lượng nhân viên
            link_web VARCHAR(50) NOT NULL,      -- trụ sở chính
            description VARCHAR(2000) NOT NULl, -- mô tả công ty
            avatar VARCHAR(50) NOT NULL,        -- ảnh đại diện
            backdrop_img VARCHAR(50) NOT NULL,  -- ảnh bìa
            is_delete INTEGER NOT NULL,
            PRIMARY KEY(id)
);