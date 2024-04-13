package net.proselyte.springbootdemo.service;
import net.proselyte.springbootdemo.model.SmsRequest;
import net.proselyte.springbootdemo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    private final RestTemplate restTemplate;

    public SmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void sendSms(User user) {

        String url = "https://notify.eskiz.uz/api/message/sms/send"; // Полный URL смс шлюза

            // Создание текста сообщения с динамическими данными о пользователе
        String message = String.format("Hurmatli %s %s, ogohlantiramiz, sizning %s dagi %s raqamli litsenziyangiz muddati yakunlandi. Litsenziyani yangilash uchun ... manzilga tashrif buyurishingiz mumkin.",
                user.getFirstName(), user.getLastName(), user.getRegDate(), user.getLisenceNum());

            // Создание заголовков запроса
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("dehqanov19930629@gmail.com", "Bearer n95bM3ysjJFJYjLPfAL62H6wGeAW2nBWNLzv2SQ9"); // Используйте Bearer token для аутентификации


        // Создание объекта запроса с номером телефона получателя и текстом сообщения
            SmsRequest smsRequest = new SmsRequest(user.getPhoneNumber(), message);

            // Создание объекта HttpEntity с запросом и заголовками
            HttpEntity<SmsRequest> requestEntity = new HttpEntity<>(smsRequest, headers);

            // Отправка POST-запроса на API SMS-шлюза
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // Проверка статуса ответа
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Обработка успешного ответа, например, логирование
                logger.info("Сообщение успешно отправлено");
                user.setSmsSent(true);
            } else {
                // Обработка неуспешного ответа
                logger.error("Ошибка при отправке сообщения: " + responseEntity.getStatusCode());
            }

        }
}
