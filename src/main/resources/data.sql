INSERT IGNORE INTO terms (id, name, content, required, version, created_at, updated_at)
VALUES (1, '만 14세 미만 약관', '<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>약관 상세</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif; line-height: 1.6; padding: 20px; color: #333; font-size: 14px; }
        h3 { font-size: 18px; margin-bottom: 10px; color: #000; border-bottom: 2px solid #333; padding-bottom: 10px; }
        h4 { font-size: 15px; margin-top: 20px; margin-bottom: 8px; color: #444; }
        p { margin: 0 0 10px 0; word-break: keep-all; }
        ul { padding-left: 20px; background-color: #f9f9f9; padding: 15px; border-radius: 8px; list-style: none; }
        li { margin-bottom: 8px; padding-left: 10px; position: relative; }
        li strong { display: block; color: #000; margin-bottom: 2px; }
        .agreement-text { font-weight: bold; margin-top: 20px; text-align: center; color: #000; }
    </style>
</head>
<body>

    <h3>만 14세 미만 미성년자 법정대리인 개인정보 약관 동의</h3>

    <p>개인정보보호법 제22조2 제1항에 의거 만 14세 미만 미성년자의 개인정보를 수집하기 위해서는 법정대리인의 동의가 필요합니다.</p>

    <h4>[개인정보 수집 및 이용에 대한 동의]</h4>
    <p>그리니(이하 ''그리니'')는 회원정보 관리, 놀이 활동 제공 및 통계 서비스 제공을 위하여 아래와 같이 개인정보를 수집·이용합니다.</p>

    <ul>
        <li>
            <strong>1. 개인정보 수집 항목 (필수)</strong>
            성명, 생년월일, 서비스 이용기록(접속 일시, 이용한 서비스 및 기능 등)
        </li>
        <li>
            <strong>2. 수집 및 이용 목적</strong>
            회원정보 관리, 놀이 활동 제공 및 관리, 서비스 이용 통계 분석
        </li>
        <li>
            <strong>3. 보유 및 이용 기간</strong>
            원칙적으로, 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체없이 파기합니다. 단, 관계법령의 규정에 의하여 보존할 필요가 있는 경우 그리니는 관계 법령에서 정한 일정기간 동안 개인정보를 보관합니다.
        </li>
    </ul>

    <p class="agreement-text">본인은 위 아동의 법정대리인으로서, 위와 같은 개인정보 수집·이용에 동의합니다.</p>

</body>
</html>', true, 1, NOW(), NOW()),
       (2, '이용약관', '<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>서비스 이용약관</title>
    <style>
        /* 모바일 최적화 기본 스타일 */
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif;
            line-height: 1.6;
            color: #333;
            margin: 0;
            padding: 20px;
            background-color: #fff;
            word-break: keep-all; /* 단어 단위 줄바꿈 (한글 가독성 핵심) */
        }

        /* 약관 전체 컨테이너 */
        .terms-container {
            max-width: 800px; /* 태블릿/PC에서도 너무 퍼지지 않게 */
            margin: 0 auto;
        }

        /* 메인 타이틀 */
        h2 {
            text-align: center;
            font-size: 20px;
            margin-bottom: 30px;
            color: #111;
            border-bottom: 2px solid #333;
            padding-bottom: 15px;
        }

        /* 챕터 (제 1 장) */
        h3 {
            font-size: 16px;
            color: #009688; /* 포인트 컬러 (브랜드 색상으로 변경 가능) */
            margin-top: 30px;
            margin-bottom: 10px;
            border-bottom: 1px solid #eee;
            padding-bottom: 5px;
        }

        /* 조항 (제 1 조) */
        h4 {
            font-size: 15px;
            color: #222;
            margin-top: 20px;
            margin-bottom: 8px;
        }

        /* 일반 텍스트 */
        p {
            margin: 0 0 10px 0;
            font-size: 14px;
            color: #555;
        }

        /* 리스트 스타일 (숫자 목록) */
        ol {
            padding-left: 20px;
            margin: 0 0 15px 0;
            font-size: 14px;
            color: #555;
        }

        li {
            margin-bottom: 8px;
            padding-left: 5px;
        }

        /* 강조 텍스트 */
        .highlight {
            font-weight: bold;
            color: #333;
        }

        /* 푸터 영역 */
        .footer {
            margin-top: 40px;
            text-align: center;
            font-size: 12px;
            color: #999;
            padding-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="terms-container">
    <h2>이용약관</h2>

    <h3>제 1 장 총칙</h3>

    <h4>제 1 조 (목적)</h4>
    <p>본 약관은 그리니(이하 "그리니")에서 제공하는 모든 서비스(이하 "서비스")의 이용조건 및 절차, 이용자의 권리, 의무, 책임사항과 기타 제반 사항을 규정함을 목적으로 합니다.</p>

    <h4>제 2 조 (약관의 명시와 개정)</h4>
    <ol>
        <li>그리니는 이 약관의 내용을 이용자가 알 수 있도록 그리니의 초기 서비스화면(전면)에 게시합니다.</li>
        <li>그리니는 약관의 규제에 대한 법률, 전자거래기본법, 전자서명법, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.</li>
        <li>그리니가 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 그리니의 초기화면에 공지합니다.</li>
        <li>그리니는 약관의 변경사항을 이용자가 알 수 있도록 공지하며, 이용자가 변경된 약관에 동의하지 않을 경우 이용계약을 해지할 수 있습니다.</li>
        <li>본 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신사업법, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 및 기타 관련 법령의 규정에 의합니다.</li>
    </ol>

    <h4>제 3 조 (용어의 정의)</h4>
    <p>본 약관에서 사용하는 용어의 정의는 다음과 같습니다.</p>
    <ol>
        <li><span class="highlight">그리니 :</span> 본 서비스의 이름</li>
        <li><span class="highlight">이용계약 :</span> 서비스 이용과 관련하여 그리니와 이용자 간에 체결하는 계약</li>
        <li><span class="highlight">가입 :</span> 그리니가 제공하는 신청서 양식에 해당 정보를 기입하고, 본 약관에 동의하여 서비스 이용계약을 완료시키는 행위</li>
        <li><span class="highlight">회원 :</span> 그리니에 회원가입에 필요한 개인 정보를 제공하여 회원 등록을 한 자로서, 그리니의 정보 및 서비스를 이용할 수 있는 자</li>
        <li><span class="highlight">이메일 :</span> 이용고객의 식별과 이용자가 서비스 이용을 위하여 이용자가 정하고 그리니가 승인하는 문자와 숫자의 조합</li>
        <li><span class="highlight">비밀번호 :</span> 이용자가 등록회원과 동일인인지 신원을 확인하고 통신상의 자신의 개인정보보호를 위하여 이용자 자신이 정한 문자와 숫자의 조합</li>
    </ol>


    <h3>제 2 장 이용계약의 성립 및 해지</h3>

    <h4>제 4 조 (이용계약의 성립)</h4>
    <ol>
        <li>이용계약은 이용자가 본 이용약관 내용에 대한 동의와 이용신청에 대하여 그리니의 이용승낙으로 성립합니다.</li>
        <li>본 이용약관에 대한 동의는 이용신청 당시 그리니의 ''동의함'' 버튼을 누름으로써 의사표시를 합니다.</li>
    </ol>

    <h4>제 5 조 (회원가입)</h4>
    <ol>
        <li>회원가입은 신청자가 온라인으로 그리니에서 제공하는 소정의 가입신청 양식에서 요구하는 사항을 기록하여 가입을 완료하는 것으로 성립됩니다.</li>
        <li>그리니는 자체 개발하여 서비스를 제공하며 변경 시 변경될 서비스의 내용을 이용자에게 공지하고 제공할 수 있습니다.</li>
        <li>회원은 등록사항에 변경이 있는 경우, 즉시 프로필 수정 등 기타 방법으로 그리니에 대하여 그 변경사항을 알려야 합니다.</li>
    </ol>

    <h4>제 6 조 (회원정보 사용에 대한 동의)</h4>
    <p>그리니의 회원 정보는 다음과 같이 수집, 사용, 관리, 보호됩니다.</p>
    <ol>
        <li><span class="highlight">개인정보의 수집 :</span> 그리니는 회원이 그리니 서비스 가입 시 제공하는 정보를 통하여 정보를 수집합니다.</li>
        <li><span class="highlight">개인정보의 사용 :</span> 그리니는 서비스 제공과 관련해서 수집된 회원의 신상정보를 본인의 승낙없이 제3자에게 누설, 배포하지 않습니다.</li>
        <li><span class="highlight">개인정보의 관리 :</span> 회원은 개인정보의 보호 및 관리를 위하여 서비스의 프로필 수정에서 수시로 본인의 개인정보를 수정할 수 있습니다.</li>
        <li><span class="highlight">개인정보의 보호 :</span> 회원의 개인정보는 오직 본인만이 열람/수정/삭제할 수 있으며, 이는 전적으로 회원의 이메일과 비밀번호에 의해 관리되고 있습니다. 따라서 타인에게 본인의 이메일과 비밀번호를 알려주어서는 아니 되며, 작업 종료 시에는 앱을 닫아야 합니다.</li>
    </ol>
    <p>회원이 그리니에 본 약관에 따라 이용신청을 하는 것은 그리니가 본 약관에 따라 신청서에 기재된 회원정보를 수집, 이용하는 것에 동의하는 것으로 간주됩니다.</p>


    <h3>제 3 장 서비스의 이용</h3>

    <h4>제 7 조 (서비스 이용시간)</h4>
    <ol>
        <li>서비스 이용시간은 그리니의 업무상 또는 기술상 특별한 지장이 없는 한 연중무휴, 1일 24시간을 원칙으로 합니다.</li>
        <li>제1항의 이용시간 중 정기점검 등의 필요로 인하여 그리니가 정한 날 또는 시간은 예외로 합니다.</li>
    </ol>

    <h4>제 8 조 (서비스의 중지 및 중지에 대한 공지)</h4>
    <ol>
        <li>이용자는 그리니 서비스에 보관되거나 전송된 메시지 및 기타 통신 메시지 등의 내용이 국가의 비상사태, 정전, 그리니의 관리 범위 외의 서비스 설비 장애 및 기타 불가항력에 의하여 보관되지 못하였거나 삭제된 경우, 전송되지 못한 경우 및 기타 통신 데이터의 손실이 있을 경우에 그리니는 관련 책임을 부담하지 아니합니다.</li>
        <li>그리니가 정상적인 서비스 제공의 어려움으로 인하여 일시적으로 서비스를 중지하여야 할 경우에는 서비스 중지 전에 중지사유 및 일시를 공지한 후 서비스를 중지할 수 있으며, 회원이 공지내용을 인지하지 못한 데 대하여 그리니는 책임을 부담하지 아니합니다. 또한 위 서비스 중지에 의하여 본 서비스에 보관되거나 전송된 메시지 및 기타 통신 메시지 등의 내용이 보관되지 못하였거나 삭제된 경우, 전송되지 못한 경우 및 기타 통신 데이터의 손실이 있을 경우에 대하여도 그리니는 책임을 부담하지 아니합니다.</li>
        <li>그리니의 사정으로 서비스를 영구적으로 중단하여야 할 경우에는 제2항에 의거합니다.</li>
        <li>그리니는 사전 공지 후 서비스를 일시적으로 수정, 변경 및 중단할 수 있으며, 이에 대하여 회원 또는 제3자에게 어떠한 책임도 부담하지 아니합니다.</li>
        <li>그리니는 긴급한 시스템 점검, 증설 및 교체 등 부득이한 사유로 인하여 예고 없이 일시적으로 서비스를 중단할 수 있으며, 새로운 서비스로의 교체 등 그리니가 적절하다고 판단하는 사유에 의하여 현재 제공되는 서비스를 완전히 중단할 수 있습니다.</li>
        <li>그리니는 국가비상사태, 정전, 서비스 설비의 장애 또는 서비스 이용의 폭주 등으로 정상적인 서비스 제공이 불가능할 경우, 서비스의 전부 또는 일부를 제한하거나 중지할 수 있습니다. 다만 이 경우 그 사유 및 기간 등을 이용자에게 사전 또는 사후에 공지합니다.</li>
        <li>그리니는 그리니가 통제할 수 없는 사유로 인한 서비스 중단의 경우(시스템관리자의 고의 과실 없는 디스크장애, 시스템다운 등)에 사전통지가 불가능하며 타인(PC통신회사, 기간통신사업자 등)의 고의과실로 인한 시스템중단 등의 경우에는 통지하지 않습니다.</li>
        <li>그리니는 서비스를 특정범위로 분할하여 각 범위별로 이용가능시간을 별도로 지정할 수 있습니다. 다만 이 경우 그 내용을 공지합니다.</li>
        <li>그리니는 회원이 본 약관의 내용에 위배되는 행동을 한 경우, 임의로 서비스 사용을 제한 및 중지하거나 회원의 동의 없이 이용계약을 해지할 수 있습니다. 이 경우 그리니는 위 이용자의 접속을 금지할 수 있습니다.</li>
        <li>그리니의 고의 또는 중대한 과실로 인하여 발생한 손해에 대해서는 관련 법령에 따라 책임을 부담합니다.</li>
    </ol>

    <h4>제 9 조 (정보 제공)</h4>
    <p>그리니는 서비스를 운영함에 있어서 각종 정보를 그리니에 게재하는 방법으로 회원에게 제공할 수 있습니다.</p>


    <h3>제 4 장 의무 및 책임</h3>

    <h4>제 10 조 (그리니의 의무)</h4>
    <ol>
        <li>그리니는 법령과 본 약관이 금지하거나 미풍양속에 반하는 행위를 하지 않으며, 지속적이고 안정적으로 서비스를 제공하기 위해 노력할 의무가 있습니다.</li>
        <li>그리니는 회원의 개인 신상 정보를 본인의 승낙 없이 타인에게 누설, 배포하지 않습니다. 다만, 전기통신관련법령 등 관계법령에 의하여 관계 국가기관 등의 요구가 있는 경우에는 그러하지 아니합니다.</li>
        <li>그리니는 이용자가 안전하게 그리니 서비스를 이용할 수 있도록 이용자의 개인정보 보호를 위한 보안시스템을 갖추어야 합니다.</li>
        <li>그리니는 이용자의 귀책사유로 인한 서비스 이용 장애에 대하여 책임을 지지 않습니다.</li>
    </ol>

    <h4>제 11 조 (회원의 의무)</h4>
    <ol>
        <li>회원가입 시에 요구되는 정보는 정확하게 기입하여야 합니다. 또한 이미 제공된 회원에 대한 정보가 정확한 정보가 되도록 유지, 갱신하여야 하며, 회원은 자신의 이메일 및 비밀번호를 제3자가 이용하게 해서는 안 됩니다.</li>
        <li>회원은 그리니의 사전 승낙 없이 서비스를 이용하여 어떠한 영리행위도 할 수 없습니다.</li>
        <li>회원은 그리니 서비스를 이용하여 얻은 정보를 그리니의 사전승낙 없이 복사, 복제, 변경, 번역, 출판, 방송 기타의 방법으로 사용하거나 이를 타인에게 제공할 수 없습니다.</li>
    </ol>


    <h3>제 5 장 기타</h3>

    <h4>제 12 조 (그리니의 소유권)</h4>
    <ol>
        <li>그리니가 제공하는 서비스, 그에 필요한 소프트웨어, 이미지, 마크, 로고, 디자인, 서비스명칭, 정보 및 상표 등과 관련된 지적재산권 및 기타 권리는 그리니에 소유권이 있습니다.</li>
        <li>모든 이용자는 그리니가 명시적으로 승인한 경우를 제외하고는 전항의 각 재산에 대한 전부 또는 일부의 수정, 대여, 대출, 판매, 배포, 제작, 양도, 재라이센스, 담보권 설정 행위, 상업적 이용 행위를 할 수 없으며, 제3자로 하여금 이와 같은 행위를 하도록 허락할 수 없습니다.</li>
    </ol>

    <h4>제 13 조 (양도금지)</h4>
    <p>회원이 서비스의 이용권한, 기타 이용계약 상 지위를 타인에게 양도, 증여할 수 없으며, 이를 담보로 제공할 수 없습니다.</p>

    <h4>제 14 조 (손해배상)</h4>
    <p>그리니는 무료로 제공되는 서비스와 관련하여, 그리니의 고의 또는 중대한 과실로 인하여 발생한 손해를 제외하고는 책임을 부담하지 않습니다.</p>

    <h4>제 15 조 (면책조항)</h4>
    <ol>
        <li>그리니는 천재지변, 전쟁 및 기타 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 대한 책임이 면제됩니다.</li>
        <li>그리니는 기간통신 사업자가 전기통신 서비스를 중지하거나 정상적으로 제공하지 아니하여 손해가 발생한 경우 책임이 면제됩니다.</li>
        <li>그리니는 서비스용 설비의 보수, 교체, 정기점검, 공사 등 부득이한 사유로 발생한 손해에 대한 책임이 면제됩니다.</li>
        <li>그리니는 이용자의 컴퓨터 오류에 의해 손해가 발생한 경우, 또는 회원이 신상정보를 부실하게 기재하여 손해가 발생한 경우 책임을 지지 않습니다.</li>
        <li>그리니는 서비스에 표출된 어떠한 의견이나 정보에 대해 확신이나 대표할 의무가 없으며 회원이나 제3자에 의해 표출된 의견을 승인하거나 반대하거나 수정하지 않습니다. 그리니는 어떠한 경우라도 회원의 서비스에 담긴 정보에 의존해 얻은 이득이나 입은 손해에 대해 책임이 없습니다.</li>
        <li>그리니는 회원 간 또는 회원과 제3자 간에 서비스를 매개로 하여 물품거래 혹은 금전적 거래 등과 관련하여 어떠한 책임도 부담하지 아니하고, 회원이 서비스의 이용과 관련하여 기대하는 이익에 관하여 책임을 부담하지 않습니다.</li>
        <li>그리니는 이용자가 서비스를 이용하여 기대하는 손익이나 서비스를 통하여 얻은 자료로 인한 손해에 관하여 책임을 지지 않으며, 회원이 본 서비스에 게재한 정보의 신뢰도 등 내용에 관하여는 책임을 지지 않습니다.</li>
        <li>그리니는 서비스 이용과 관련하여 이용자에게 발생한 손해 중 이용자의 고의, 과실에 의한 손해에 대하여 책임을 부담하지 아니합니다.</li>
        <li>그리니는 그리니가 제공한 서비스가 아닌 가입자가 제공하는 서비스의 내용상의 정확성, 완전성 및 질에 대하여 보장하지 않습니다. 따라서 그리니는 이용자가 위의 내용을 이용함으로 인하여 입게 된 모든 종류의 손실이나 손해에 대하여 책임을 부담하지 아니합니다. 또한 그리니는 이용자가 서비스를 이용하여 타 이용자로 인해 입게 되는 정신적 피해에 대하여 보상할 책임을 지지 않습니다.</li>
    </ol>

    <div class="footer">
        ⓒ Greeny. All rights reserved.
    </div>

</div>

</body>
</html>', true, 1, NOW(), NOW()),
       (3, '개인정보 수집·이용 동의서', '<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>개인정보 수집 및 이용 동의</title>
    <style>
        /* 공통 스타일 (이전 약관과 통일) */
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif;
            line-height: 1.6;
            color: #333;
            margin: 0;
            padding: 20px;
            background-color: #fff;
            word-break: keep-all;
        }

        .terms-container {
            max-width: 800px;
            margin: 0 auto;
        }

        h2 {
            text-align: center;
            font-size: 18px;
            margin-bottom: 25px;
            color: #111;
            border-bottom: 2px solid #333;
            padding-bottom: 15px;
        }

        /* 항목 제목 (가, 나, 다...) */
        h4 {
            font-size: 15px;
            color: #009688; /* 그리니 테마 컬러 */
            margin-top: 25px;
            margin-bottom: 10px;
            border-bottom: 1px solid #f0f0f0;
            padding-bottom: 5px;
        }

        /* 리스트 스타일 */
        ul {
            padding-left: 20px;
            margin: 0;
            list-style-type: disc;
        }

        li {
            margin-bottom: 8px;
            font-size: 14px;
            color: #555;
            padding-left: 5px;
        }

        /* 강조 텍스트 */
        .highlight {
            font-weight: bold;
            color: #333;
            background-color: #f9f9f9;
            padding: 2px 4px;
            border-radius: 4px;
        }

        /* 중요 경고/안내 */
        .notice {
            color: #d32f2f; /* 붉은색 계열 */
            font-size: 13px;
        }

        /* 링크 스타일 */
        a {
            color: #007bff;
            text-decoration: none;
        }

        /* 하단 동의 문구 */
        .agreement-statement {
            margin-top: 40px;
            padding: 15px;
            background-color: #f5f5f5;
            border-radius: 8px;
            text-align: center;
            font-weight: bold;
            font-size: 14px;
            color: #333;
        }
    </style>
</head>
<body>

<div class="terms-container">
    <h2>개인정보 수집 및 이용 동의서</h2>

    <h4>가. 수집 및 이용 목적</h4>
    <ul>
        <li>''그리니''는 서비스 제공 및 운영을 위하여 필요한 최소한의 범위 내에서 개인정보를 수집ㆍ이용합니다. 귀하는 아래 내용을 충분히 숙지한 후 동의 여부를 결정할 권리가 있습니다.</li>
        <li class="notice">필수 항목에 대한 동의를 거부하실 경우 서비스 이용이 제한될 수 있으며, 선택 항목에 동의하지 않더라도 별도의 불이익은 없습니다.</li>
        <li><strong>개인정보 처리자 :</strong> 그리니</li>
        <li><strong>개인정보 보호 문의 :</strong> <a href="mailto:greeni4child@gmail.com">greeni4child@gmail.com</a></li>
    </ul>

    <h4>나. 수집 및 이용 항목</h4>
    <ul>
        <li>
            <span class="highlight">필수항목 :</span> 성명(한글), 생년월일, 아이디, 로그인 인증번호
        </li>
    </ul>

    <h4>다. 개인정보의 보유 및 이용 기간</h4>
    <ul>
        <li>사용자의 개인정보 수집ㆍ이용에 관한 동의일로부터 <span class="highlight">탈퇴 시까지</span> 위 이용목적을 위하여 보유 및 이용하게 됩니다.</li>
        <li>회원 탈퇴 시, 수집된 개인정보는 <strong>지체 없이 파기</strong>됩니다.</li>
    </ul>

    <h4>라. 동의를 거부할 권리 및 불이익</h4>
    <ul>
        <li>위 개인정보 중 필수정보의 수집ㆍ이용에 관한 동의는 ''그리니'' 활동의 진행을 위해 필수적이므로, 위 사항에 동의하셔야만 활동에 참여가 가능합니다.</li>
    </ul>

    <div class="agreement-statement">
        본인은 위 내용을 충분히 이해하였으며,<br>
        ''그리니''가 위와 같이 개인정보를<br>
        수집ㆍ이용하는 것에 동의합니다.
    </div>

</div>

</body>
</html>', true, 1, NOW(), NOW());