package com.telebotZodiac.bot.service;

import com.telebotZodiac.bot.entity.Shelter;
import com.telebotZodiac.bot.repository.ShelterRepository;
import com.telebotZodiac.bot.shelter.ShelterGoroskop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Slf4j
@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public String getDescription(ShelterGoroskop type) {
        Optional<Shelter> shelter = shelterRepository.findFirstByType(type);
        if (shelter.isPresent()) {
            return shelter.get().getDescription();
        }
        log.info("запускаем Дескрипшн (инфо) гороскопа");
        return "Тут заглушка на getInfo :(";
    }

    public String getInstruction(ShelterGoroskop type) {
        Optional<Shelter> shelter = shelterRepository.findFirstByType(type);
        if (shelter.isPresent()) {
            return shelter.get().getInstruction();
        }
        log.info("Запускаем по дате рождения методы");
        return "Тут пока что пусто :(";
    }
    public String calculateChineseHoroscope(int day, int month, int year) {
        if (year < 1924) {
            System.out.println("Год должен быть не меньше 1924");
            return "Год не меньше 1924";
        } else if (day > 31) {
            return "Введите корректный день";
        } else if (month > 12) {
            return "вы ввели не правильный месяц";
        }
        // Знаки китайского зодиака
        String[] chineseZodiacSigns = {
                miceChina(), moolChina(), tigerChina(), rabbitChina(), dragonChina(), snakeChina(),
                horseChina(), sheepChina(), monkeyChina(), chikenChina(), dogChina(), pigChina()
        };

        // Год начала китайского календаря (китайский год 1)
        int startYear = 1924;

        // Рассчитываем китайский год на основе года рождения
        int chineseYear = (year - startYear) % 60;

        // Рассчитываем китайский месяц (для просчета стихий, пока не используем)
        int chineseMonth = month;

        // Рассчитываем китайский день (для просчета стихий пока не используем)
        int chineseDay = day;

        // Определяем знак китайского зодиака
        String zodiacSign = chineseZodiacSigns[chineseYear % 12];
        return " Ваш год: " + zodiacSign;
    }

    private String miceChina() {
        return "Крыса. \n" +
                "Год Крысы — первый в лунном цикле Восточного гороскопа, после него идут ещё 11 периодов, каждый из которых находится под покровительством своего зодиакального животного. Первый год цикла отличается от других определёнными особенностями. Так и люди, рождённые в период Крысы, отличаются от представителей остальных знаков китайского гороскопа. \n" +
                "Крыса в восточной философии — знак, символизирующий природную хитрость, способность максимально приспосабливаться к внешним, порой очень сложным условиям. Этот грызун обладает неординарными способностями и может добиться успеха во многих областях, не витает в облаках, реально смотрит на жизнь, что помогает меньше разочаровываться или почти не разочаровываться в реальности.";
    }

    private String moolChina() {
        return "Бык. \n" +
                "Люди, появившиеся на свет в год Быка, приземленные и упорные, способные и готовые много работать, чтобы добиваться поставленных целей.\n" +
                "Во всех культурах бык — символ упорного и порой очень тяжелого труда. Восточная философия не исключение: главной характеристикой родившихся в год Быка называют трудолюбие, способность много и нелегко работать. Что еще символизирует это копытное животное в китайской астрологии? Чем отличаются от остальных люди, живущие под его покровительством, с кем они хорошо ладят, а с кем отношений лучше избегать. И какие важные в мировом масштабе события происходили в год Быка — все это и не только подробно разбираем в статье.>";
    }

    private String tigerChina() {
        return "Тигр. \n" +
                "Опасный хищник, который в восточной философии является символом могущества и процветания, благоволит переменам.\n" +
                "Тигр стоит третьим в зодиакальном цикле из 12-ти почитаемых животных. Он завоевал это место в гонке, уступив только хитрой Крысе и Быку. Год Тигра описывается как время перемен и движения вперед.\n" +
                "Смелость, уверенность в себе, непредсказуемость — черты, которыми наделяет Тигр рожденных в его год. Такие люди решительны, своевольны, не боятся рисковать и стремятся к победе.";
    }

    private String rabbitChina() {
        return "Кролик. \n" +
                "Этот год несет в себе уют, дипломатию, гостеприимство и теплоту. Позитивные качества: умный, наблюдательный, развитой, миролюбивый, дружелюбный, романтичный и очень предан тем, кого любит. Негативные качества: беспокоен, боязлив, педантичен и слишком занят собой.\n" +
                "Кролик - хороший друг и чувственный любовник, обладает хорошими манерами, любит общаться, умеет создать тонкую связь с другим человеком. Внешне он всегда элегантен, любезен в общении, помогает попавшим в бедственное положение людям. Удачливый и позитивный знак.";
    }

    private String dragonChina() {
        return "Дракон. \n" +
                "Характеристика года Дракона будет неполной без небольших уточнений. Китайская астрология берет за основу пять элементов: огонь, дерево, воду, землю и металл. Иначе их еще называют стихиями. Каждая из них оказывает свое влияние на год и, как следствие, на животное китайского гороскопа. Давайте подробнее рассмотрим особенности.\n" +
                "Мужчины-Драконы очень заботятся о ближних, но сами они довольно эгоистичны. Зато харизматичны, за что им многое прощается. Имеют толпы воздыхательниц. А также множество планов: настолько, что нередко распыляются и не доводят начатое до конца. Мужчины-Драконы могут ставить далекие от реальной жизни цели и упорно их добиваться. Что не всегда хорошо сказывается на семейной жизни.\n" +
                "Женщины-Драконы ценят себя. Они слегка высокомерны, любят внимание и часто оказываются в самом его центре. Обладают отличным вкусом. Драконы бережно заботятся о себе и следят за своей внешней красотой, в которой присутствует внутреннее обаяние. Разумеется, это делает женщин-Драконов очень привлекательными для противоположного пола.";
    }

    private String snakeChina() {
        return "Змея.\n" +
                "Змеи, какие они? Мудрые, спокойные, величественные, справедливые. Но уж точно лучше не наступать им на хвост! \n" +
                "Мыслители и собиратели тайн — вот они, люди, рожденные в год Змеи. Мудрые и хладнокровные, они легко могут размотать клубок самых запутанных интриг. Среди этих людей нередко встречаются политики, финансисты, эзотерики, психологи. Змеи гибкие в общении и всегда уверенно идут к своей цели.";
    }

    private String horseChina() {
        return "Лошадь.\n" +
                " Люди, рождённые в год Лошади – активисты и авантюристы." +
                " Они имеют широкий круг общения и всегда находятся в центре внимания." +
                " Благодаря врождённому трудолюбию легко занимают лидерские позиции и руководящие должности." +
                " Расскажем подробнее об этом знаке китайского зодиака.\n" +
                "Знак Лошади в китайском гороскопе — символ независимости и движения. Представителям этого знака комфортнее работать в одиночку, нежели в группе. И исходя из стремления к свободе, появляется огромная тяга к путешествиям. \n" +
                "\n" +
                "Говоря о Лошади, как о любовном партнёре, следует отметить такие качества, как верность и заботу. Поэтому, находясь в отношениях с ними, можно быть уверенным в завтрашнем дне."
                ;
    }

    private String sheepChina() {

        return "Коза. \n" +
                "Люди, рождённые в год Козы, отменные актёры. Отличительной чертой является готовность прийти на помощь всем нуждающимся." +
                " Благородная Коза прекрасный собеседник, так как она очень начитана и образована.\n" +
                "Коза — символ гармонии и миролюбия, поэтому люди, рождённые в год Козы щедро наделены этими качествами. В силу своей уступчивости, человек-Коза не стремится к обретению власти и продвижению по карьерной лестнице.\n" +
                "\n" +
                "Любовь к человеку восполняет пробел любви к работе, поэтому в отношениях представители этого знака весьма чувственны и романтичны.";
    }

    private String monkeyChina() {
        return " Обезьяна. \n" +
                "Люди, рожденные в год Обезьяны, имеют экспрессивный характер. В статье расскажем о сильных и слабых сторонах этого знака и других присущих ему характеристиках\n" +
                "В китайском гороскопе Обезьяна — хитрый зверь, который всегда бросает вызов тому, кто сильнее, и не боится преград. Следующий год Обезьяны будет в 2028: он пройдет под покровительством Земляной Обезьяны. На Востоке считается, что все состоит из 5 веществ: воды, дерева, огня, земли и металла. Поэтому знак зодиака соответствует определенному веществу и наделяется его характеристиками. Вместе с астрологом мы выяснили, чем отличаются люди, рожденные в год Обезьяны, что приносит им удачу и с какими знаками у них высокая совместимость.";
    }

    private String chikenChina() {
        return "Петух. \n" +
                "В Китае петух — настоящий предвестник добра.\n" +
                "Китайцы привыкли считать, что своим криком Петух отгоняет злых духов темноты. Он олицетворяет собой свет и надежду. Представитель этого знака Китайского гороскопа отличается сообразительностью, настойчивостью и сердечным складом характера. Петуху не свойственно отступать, ему нравится быть первым и направлять других. ";
    }

    private String dogChina() {
        return "Собака.\n" +
                "«Иметь в окружении много Собак» — будет хорошим пожеланием на китайский Новый год. Ведь эти люди отличаются преданностью, они надежные друзья.\n" +
                "С детства нас учат: собака — друг человека. Принято восхвалять их верность, готовность защитить хозяина ценой собственной жизни. И кстати, заметили, что в детских мультиках и литературе это животное никогда не позиционируют как злодея или подлеца?\n" +
                "\n" +
                "В китайском гороскопе этому знаку присущи те же черты, что мы приписываем нашим четвероногим братьям: дружелюбие, преданность.";
    }

    private String pigChina() {
        return "Свинья. \n" +
                "Неспроста традиционные копилки делают в виде хрюшки: со времен Древнего Китая свинья считается символом изобилия.\n" +
                "Людей, рожденных в год Кабана, объединяет трудолюбие, общительность и стремление жить на широкую ногу. Но нельзя сказать, что так уж одинаковы все люди этого знака. Расскажем, какие Кабаны бывают, как влияет на их характер и привычки стихия, под символом которой они родились.";
    }
    public String getZodiacSign(int day, int month) {

        if ((month == 3 && day >= 21 && day <= 31) || (month == 4 && day <= 19)) {
            return oven();
        } else if ((month == 4 && day >= 20 && day <= 30) || (month == 5 && day <= 20)) {
            return " Тельцы — личности очень сильные и об этом уже было сказано выше." +
                    " Но когда их личные качества становятся гипертрофированными и выходят за рамки, с ними становится очень тяжело. Они упорны и трудолюбивы, готовы годами много трудиться, чтобы добиться желаемого. Но бывает, что эти прекрасные свойства натуры при определённых обстоятельствах трансформируются в недостатки. И тогда человек становится излишне упрямым, старается добиться того, что на самом деле не нужно, идет к псевдоцелям.";
        } else if ((month == 5 && day >= 21 && day <= 31) || (month == 6 && day <= 20)) {
            return " Близнецы. Близнецы любят много разнообразного общения, которое расширяет их кругозор. У них множество приятелей и знакомых. Но по-настоящему крепко дружат они очень редко. Ведь дружба — это уже глубокое погружение в человека. А это отнимает у Близнецов слишком много энергии и душевных сил. С близкими людьми могут демонстрировать то, чего не показывают всем остальным: душевную нежность и ранимость.";
        } else if ((month == 6 && day >= 21 && day <= 30) || (month == 7 && day <= 22)) {
            return " Рак. Сильный и слабый, душевный и холодный, коммуникабельный и затворнический — все это Рак. Детство, юность, семья, упавшие (или нет) на голову проблемы — все это может создать из одного и того же Рака диаметрально противоположных людей.";
        } else if ((month == 7 && day >= 23 && day <= 31) || (month == 8 && day <= 22)) {
            return " Лев — генератор любви. Он делится ей со всеми, но необычайно много любви с его стороны получает именно партнер и другие члены семьи. Харизма и темпераментность, яркость и огонь в глазах — Львы любвеобильны, романтичны, не боятся проявлять себя в отношениях, погружаться в мир партнера.";
        } else if ((month == 8 && day >= 23 && day <= 31) || (month == 9 && day <= 22)) {
            return " Дева — настоящий реалист. Она смотрит на жизнь так, что та иногда кажется ей немного страшной. Но при этом Дева не боится брать ответственность, умеет управляться с деньгами и знает им цену. Ценности Дев часто не совпадают с ценностями большинства людей. Для них гораздо важнее порядок, стабильность, семья. При этом представитель знака очень чувствителен к энергии окружающих. Хоть разум и берет верх над чувствами, Деве часто необходимо время, чтобы прийти в себя после общения с большим количеством людей.";
        } else if ((month == 9 && day >= 23 && day <= 30) || (month == 10 && day <= 22)) {
            return " Весы. К достоинствам Весов можно отнести обаяние, дружелюбие, общительность, дипломатичность, справедливость, честность, тактичность, креативность, острый ум, логика, прекрасный вкус. \n" +
                    "\n" +
                    "Недостатки у них тоже имеются. К ним относятся: непостоянство, нерешительность, неумение принимать решения, страх сделать ошибку, оторванность от реальности, непрактичность.";
        } else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day <= 21)) {
            return " Скорпион. Достоинства Скорпиона: сила характера, стойкость, упорство, целеустремленность, амбициозность, верность, бескорыстность, терпение, стремление к совершенству, сильная интуиция.\n" +
                    "\n" +
                    "Недостатки: властность, ревность, мстительность, скрытность, агрессивность, обидчивость, эгоизм.";
        } else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day <= 21)) {
            return " Стрелец. Стрельцы очень влюбчивы, они готовы быстро увлекаться и вспыхивать от новых чувств. Главным минусом этих романтиков является непостоянство. Стрельцы могут долгое время идеализировать партнера, а потом по щелчку «прозреть». Если несчастный изгнан с пьедестала, то ему не поздоровится.\n" +
                    "\n" +
                    "Обидчивость Стрельцов может порождать конфликты. Музе Стрельца стоит приготовиться к не всегда оправданной ревности. В то же время, в отношениях Стрельцы щедры на ласку, презенты и теплые слова.";
        } else if ((month == 12 && day >= 22 && day <= 31) || (month == 1 && day <= 19)) {
            return " Козерог \n" +
                    "Достоинства:\n" +
                    "\n" +
                    "Способность к самодисциплине, решительность, практичность, амбициозность, расчетливость, упорство в достижении цели, проницательность, постоянство, трудолюбие, вежливость.\n" +
                    "\n" +
                    "Недостатки:\n" +
                    "\n" +
                    "Своенравность, властолюбие, коварство, жестокость, любовь к роскоши и комфорту. Не готовы подпускать к себе новых людей.";
        } else if ((month == 1 && day >= 20 && day <= 31) || (month == 2 && day <= 18)) {
            return " Водолей. Если говорить о сильных сторонах Водолея, то, в первую очередь, это его целеустремленность, умение найти выход из любой сложной ситуации, ответственность за себя и своих близких. Причем ответственны Водолеи во всем — на работе, перед начальством, перед друзьями, а главное — перед своей семей. А еще Водолеи довольно часто бывают прекрасными родителями, которые умеют найти подход к своим детям, не ограничиваясь скучными штампами из книг по воспитанию.";
        } else if ((month == 2 && day >= 19 && day <= 29) || (month == 3 && day <= 30)) {
            return " Рыбы. Главным достоинством Рыб в наше время является честность. Даже если в некоторых случаях будет выгодно солгать, в силу их добропорядочности этого не случится.\n" +
                    "\n" +
                    "Трудолюбие присуще Рыбам, но только тогда, когда никто не видит. Так как представители этого знака — творческие натуры, для работы им нужно уединиться. Водные знаки не любят делать что-то напоказ, поэтому и трудиться привыкли в одиночку.";
        } else return " Вы ввели дичь! Введите корректную дату дд.мм, пример 19.11";
    }

    //можно и так чекнуть
    private String oven() {
        return " Овен личность крайне целеустремлённая. Что можно записать как в плюс, так и в минус, представителю знака. Если он действительно чем-то увлечён, он, без преувеличения, готов на всё ради достижения цели. Главное — успеть её достичь до утраты интереса.";
    }

}
