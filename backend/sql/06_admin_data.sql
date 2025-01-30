INSERT INTO `user` (`id`, `user_name`, `password`, `enabled`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES (999, 'admin', '$2a$10$RNK3LYadnm/K5iLlFX7zPu/6sOWhl6.a4hGlpBVX4QDti4urwRYee', 1, '2025-01-28 00:13:04.640421',
        'anonymousUser', '2025-01-28 00:13:04.640421', 'anonymousUser');


INSERT INTO `user_role` (`user_id`, `role_id`, `created_at`, `created_by`, `updated_at`, `updated_by`)
VALUES (999, 2, '2025-01-28 00:16:38.493108', 'anonymousUser', '2025-01-28 00:16:38.493108', 'anonymousUser');