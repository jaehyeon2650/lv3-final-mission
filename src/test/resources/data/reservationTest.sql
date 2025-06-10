insert into member(id,username,birth, email, password) values(1,'cogi','2000-11-02','ind07152@naver.com','1234');
insert into member(id,username,birth, email, password) values(2,'vector','2000-11-03','ind07162@naver.com','1234');

insert into reservation_time(id,time) values(1,'10:00');
insert into reservation_time(id,time) values(2,'11:00');
insert into reservation_time(id,time) values(3,'12:00');
insert into reservation_time(id,time) values(4,'13:00');
insert into reservation_time(id,time) values(5,'14:00');
insert into reservation_time(id,time) values(6,'15:00');

insert into reservation(id,reservation_date,member_id,time_id) values (1,'2025-06-11',1,1);
insert into reservation(id,reservation_date,member_id,time_id) values (2,'2025-06-11',1,2);
insert into reservation(id,reservation_date,member_id,time_id) values (3,'2025-06-11',2,3);
insert into reservation(id,reservation_date,member_id,time_id) values (4,'2025-06-11',2,4);