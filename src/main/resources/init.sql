insert into member(username,birth, email, password) values('cogi','2000-11-02','ind07152@naver.com','1234');
insert into member(username,birth, email, password) values('vector','2000-11-03','ind07162@naver.com','1234');

insert into reservation_time(time) values('10:00');
insert into reservation_time(time) values('11:00');
insert into reservation_time(time) values('12:00');
insert into reservation_time(time) values('13:00');
insert into reservation_time(time) values('14:00');
insert into reservation_time(time) values('15:00');

insert into reservation(reservation_date,member_id,time_id) values ('2025-06-11',1L,1L);
insert into reservation(reservation_date,member_id,time_id) values ('2025-06-11',1L,2L);
insert into reservation(reservation_date,member_id,time_id) values ('2025-06-11',2L,3L);
insert into reservation(reservation_date,member_id,time_id) values ('2025-06-11',2L,4L);