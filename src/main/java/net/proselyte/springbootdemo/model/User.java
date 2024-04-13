package net.proselyte.springbootdemo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "reg_date")
    private LocalDate regDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "lisence_num")
    private String lisenceNum;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "is_sms_sent")
    private boolean isSmsSent;

    public void updateIsActive() {
        LocalDate currentDate = LocalDate.now();
        LocalDate registrationDate = this.getRegDate();

        // Проверяем, прошло ли более 3 месяцев с момента регистрации
        if (registrationDate.plusMonths(3).isBefore(currentDate)) {
            this.isActive = false;
            this.isSmsSent = true; // Помечаем, что смс уже было отправлено
        }
    }


}
