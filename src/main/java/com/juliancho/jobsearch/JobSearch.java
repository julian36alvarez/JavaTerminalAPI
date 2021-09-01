package com.juliancho.jobsearch;

import com.beust.jcommander.JCommander;
import com.juliancho.jobsearch.api.APIJobs;
import com.juliancho.jobsearch.cli.CLIArguments;
import com.juliancho.jobsearch.cli.CLIFunctions;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.juliancho.jobsearch.api.APIFunctions.buildAPI;
import static com.juliancho.jobsearch.cli.CommanderFunctions.buildCommanderWhitName;
import static com.juliancho.jobsearch.cli.CommanderFunctions.parseArguments;

public class JobSearch {
    public static void main(String[] args) {
        JCommander jCommander = buildCommanderWhitName("job-search", CLIArguments::newInstance);
        Stream<CLIArguments> streamOfCLI =
                parseArguments(jCommander, args, JCommander::usage)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(obj -> (CLIArguments) obj);
        Optional<CLIArguments> cliArgumentsOptional =
                streamOfCLI.filter(cli -> !cli.isHelp())
                        .filter(cli -> cli.getKeyword() != null)
                        .findFirst();
        cliArgumentsOptional.map(CLIFunctions::toMap)
                .map(JobSearch::executeRequest)
                .orElse(Stream.empty())
                .forEach(System.out::println);
    }

    private static Stream<JobPosition> executeRequest(Map<String, Object> params){
        APIJobs  api = buildAPI(APIJobs.class, "https://jobs.github.com");
        return Stream.of(params)
                .map(api::jobs)
                .flatMap(Collection::stream);
    }

}
