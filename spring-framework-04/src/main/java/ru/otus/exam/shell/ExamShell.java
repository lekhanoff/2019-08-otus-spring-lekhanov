package ru.otus.exam.shell;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Student;
import ru.otus.exam.service.ExamService;

@ShellComponent
public class ExamShell {

    private final ExamService examService;

    private final MessageSource messageSource;
    
    private final String locale;
    
    private Student student;

    private ExamResult examResult;

    public ExamShell(ExamService examService, MessageSource messageSource, ApplicationSettings applicationSettings) {
        this.examService = examService;
        this.messageSource = messageSource;
        this.locale = applicationSettings.getLocale();
    }
    
    @PostConstruct
    public void init() {
        setLocale();
    }

    @ShellMethod(value = "Login command", key = {"login", "l"})
    public void login() throws IOException {
        examResult = null;
        student = examService.login();
    }

    @ShellMethod(value = "Start exam command", key = {"start", "st"})
    @ShellMethodAvailability(value = "loginAvailabilityCheck")
    public void startExam() throws IOException {
        examResult = examService.examine();
    }
    
    @ShellMethod(value = "Get exam result command", key = { "result", "rs" })
    @ShellMethodAvailability(value = "examResultAvailabilityCheck")
    public String examResult() {
        return examResult.getResultMessage(student.getFirstname());
    }

    public Availability loginAvailabilityCheck() {
        return student != null ? Availability.available()
                : Availability.unavailable(
                        messageSource.getMessage("command.login.not.available", new Object[] {}, Locale.getDefault()));
    }

    public Availability examResultAvailabilityCheck() {
        return examResult != null ? Availability.available()
                : Availability.unavailable(messageSource.getMessage("command.exam-result.not.available",
                        new Object[] {}, Locale.getDefault()));
    }
    
    private void setLocale() {
        List<String> localeList = Arrays.asList(locale.split("_"));
        if(!localeList.isEmpty() && localeList.size() > 1) {
            Locale.setDefault(new Locale(localeList.get(0), localeList.get(1)));
        }
    }

}
