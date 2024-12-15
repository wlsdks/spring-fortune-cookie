package io.github.wlsdks.fortunecookie.provider;

import io.github.wlsdks.fortunecookie.properties.FortuneCookieProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

/**
 * 포춘 메시지를 제공하는 기본 구현체입니다.
 * 포춘 메시지는 메시지 프로퍼티 파일에서 랜덤하게 가져옵니다.
 */
@Component
public class DefaultFortuneProvider implements FortuneProvider {

    private final MessageSource messageSource;
    private final FortuneCookieProperties properties;
    private final Random random;

    private static final String MESSAGE_PREFIX = "fortune."; // 메시지 프로퍼티 접두어

    public DefaultFortuneProvider(MessageSource messageSource, FortuneCookieProperties properties) {
        this.messageSource = messageSource;
        this.properties = properties;
        this.random = new Random();
    }

    /**
     * 포춘 키를 생성합니다.
     * 랜덤으로 인덱스를 선택하여 키를 생성합니다.
     *
     * @return 포춘 메시지 키
     */
    @Override
    public String generateFortuneKey() {
        // fortune 파일에 정의된 메시지 수 기반으로 랜덤 선택
        int index = random.nextInt(properties.getFortunesCount()) + 1; // 1부터 N까지
        return MESSAGE_PREFIX + index;
    }

    /**
     * 지정된 로케일에 맞는 포춘 메시지를 반환합니다.
     *
     * @param fortuneKey 포춘 메시지 키
     * @param locale     메시지를 가져올 로케일
     * @return 랜덤하게 선택된 포춘 메시지 또는 기본 메시지
     */
    @Override
    public String getFortune(String fortuneKey, Locale locale) {
        try {
            return messageSource.getMessage(fortuneKey, null, locale);
        } catch (Exception e) {
            // 키가 없거나 다른 문제가 발생했을 때 기본 메시지 반환
            return messageSource.getMessage("fortune.default", null,
                    "Today is your lucky day!", locale);
        }
    }

}
