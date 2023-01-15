insert into roles (name)
values ('ROLE_ADMIN'), ('ROLE_USER');
insert into users (email, password, username)
values ('admin@admin.admin', '$2a$10$61xddWey9oMVzR.D2UyYeO4OlQ2nviVljXG8ThmlDhfbISRjMtvL6', 'admin');
insert into user_roles (user_id, role_id)
values (1, 1);
