pipelineJob('mariadb-docker-prod') {
    definition {
        cpsScm {
            scm {
                github('ukhc/registry-docker', 'master', 'https')
            }
        }
    }
}
pipelineJob('mariadb-docker-qa') {
    definition {
        cpsScm {
            scm {
                github('ukhc/registry-docker', 'qa', 'https')
            }
        }
    }
}