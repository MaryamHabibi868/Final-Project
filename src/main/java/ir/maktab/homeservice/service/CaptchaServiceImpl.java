package ir.maktab.homeservice.service;

import ir.maktab.homeservice.dto.CaptchaDto;
import ir.maktab.homeservice.util.CaptchaUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CaptchaServiceImpl implements CaptchaService {


    // برای سادگی از یک Map در حافظه استفاده می‌کنیم؛ در محیط واقعی Redis یا DB.
    private final Map<String, String> store = new ConcurrentHashMap<>();


    @Override
    public CaptchaDto generate() {
        String[] cap = CaptchaUtil.generate();
        String text      = cap[0];
        String base64Img = cap[1];

        // متن را به‌عنوان توکن برمی‌گردانیم و نگه می‌داریم
        store.put(text, text);

        return new CaptchaDto(text, base64Img);
    }

    @Override
    public boolean verify(String token, String userAnswer) {
        if (token == null || userAnswer == null) return false;
        String expected = store.get(token);
        boolean ok = expected != null && expected.equalsIgnoreCase(userAnswer.trim());
        if (ok) store.remove(token);            // یک‌بار مصرف
        return ok;
    }
}
