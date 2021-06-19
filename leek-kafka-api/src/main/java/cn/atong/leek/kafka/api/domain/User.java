package cn.atong.leek.kafka.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description  User Entity
 * @author atong
 * @date 19:43 2021/6/19
 * @version 1.0.0.1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
}