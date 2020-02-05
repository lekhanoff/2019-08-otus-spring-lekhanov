package ru.otus.lib.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.service.MigrationBatchService;

@RequiredArgsConstructor
@ShellComponent
public class MigrationShell {

    private final MigrationBatchService batchService;

    @ShellMethod(value = "Migrate data to Mongo", key = {"migrate", "m"})
    public void migrate() {
        batchService.launchJob();
    }
}
