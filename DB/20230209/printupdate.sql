ALTER TABLE `dxz`.`print_api`
    ADD COLUMN `type` tinyint(1) NULL COMMENT '类型' AFTER `dedcr`;

ALTER TABLE `dxz`.`print_params`
    ADD COLUMN `type` tinyint(1) NULL COMMENT '参数类型' AFTER `data_api`;


ALTER TABLE `dxz`.`printer_instance`
    ADD COLUMN `printer_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印机秘钥' AFTER `printer_name`;
ALTER TABLE `dxz`.`printer_instance`
    ADD COLUMN `making_status` tinyint(1) NULL DEFAULT 2 COMMENT '打印制作单 1是 2否' AFTER `descr`;
ALTER TABLE `dxz`.`printer_instance`
    ADD COLUMN `store_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '门店id' AFTER `printer_spec`;