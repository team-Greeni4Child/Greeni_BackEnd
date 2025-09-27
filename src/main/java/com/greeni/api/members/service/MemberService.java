package com.greeni.api.members.service;

import com.greeni.api.apiPayload.exception.GeneralException;
import com.greeni.api.apiPayload.status.ErrorStatus;
import com.greeni.api.members.converter.MemberConverter;
import com.greeni.api.members.domain.Member;
import com.greeni.api.members.dto.MemberRequestDTO;
import com.greeni.api.members.dto.MemberResponseDTO;
import com.greeni.api.members.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, Object> redistemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberResponseDTO.toJoinResultDTO signUpMember(MemberRequestDTO.SignUpDTO request) {
        // 해당 메일이 이미 존재하는 메일인지 확인
        if(memberRepository.existsByEmail(request.getEmail())){
            throw new GeneralException(ErrorStatus.EXIST_EMAIL);
        }

        // 이메일 인증 완료 여부 확인
        ValueOperations<String, Object> ops = redistemplate.opsForValue();
        String codeNum = (String) ops.get("EmailCode"+request.getEmail());
        if(codeNum == null){
            throw new GeneralException(ErrorStatus.EXPIRED_CODE);
        }
        if(!codeNum.equals(request.getCode())){
            throw new GeneralException(ErrorStatus.WRONG_CODE);
        }

        Member member = MemberConverter.toMember(request);
        member.encodePassword(bCryptPasswordEncoder.encode(member.getPassword()));
        memberRepository.save(member);

        return MemberConverter.toJoinResultDTO(member);
    }

    // 이메일 인증 번호 전송
    public void sendEmail(MemberRequestDTO.EmailDTO request) {
        int number = (int)(Math.random() * 90000)+100000;

        try{
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true,  "UTF-8");
            helper.setSubject("[Greeni] 이메일 인증 번호");
            helper.setTo(request.getEmail());
            helper.setFrom(new InternetAddress("greeni@greeni.com", "greeni", "UTF-8"));

            String body = "";
            body += "<!DOCTYPE html>";
            body += "<html lang='ko'>";
            body += "<head><meta charset='UTF-8'></head>";
            body += "<body style='margin:0;padding:0;background-color:#f6f7f8;font-family:sans-serif;'>";

            body += "<div style='width:100%;padding:30px 0;'>";
            body += "  <div style='max-width:600px;margin:0 auto;background:#ffffff;"
                    + "border-radius:8px;padding:30px;box-shadow:0 4px 12px rgba(0,0,0,0.08);'>";

            body += "    <h3 style='margin-top:0;color:#333;'>Greeni의 인증번호입니다.</h3>";
            body += "    <h1 style='font-size:32px;color:#2d3748;text-align:center;margin:20px 0;'>"
                    + number + "</h1>";
            body += "    <p style='font-size:15px;color:#555;line-height:1.6;'>"
                    + "화면으로 돌아가 인증번호를 입력해주세요<br/>"
                    + "인증번호는 발송된 시점부터 <b>3분</b> 동안 유효합니다.<br/>"
                    + "유효시간 내 인증을 완료하지 않을 경우 재요청이 필요합니다."
                    + "</p>";

            body += "  </div>";
            body += "</div>";

            body += "</body></html>";

            helper.setText(body, true);

            javaMailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e){
            log.error("Error Sending email", e);
        }

        // redis에 인증번호 3분간 저장
        ValueOperations<String, Object> ops = redistemplate.opsForValue();
        ops.set("EmailCode"+request.getEmail(), number+"", 180, TimeUnit.SECONDS);
    }

}
