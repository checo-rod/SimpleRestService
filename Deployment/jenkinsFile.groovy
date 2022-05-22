@Library(["automationservices-groovy-commonlibrary", "automationservices-groovy-commonpipeline"]) _ // import libraries

Map params = pipelineInitializer.setup("MAVEN-OPENSHIFT", env, [
  appName         : "createorder",
  gitRepo         : "im-java-createorder",
  projectName     : "LotteryServices",
  hasCLEConfig    : false,
  hasEMSConfig    : false,
  cleanTempFiles  : true
])


params.technology.agent.build.image = "lin-maven-openshift-java-11"

params.stages.build.saveArtifacts.postStageContent = {
   
	echo "Build successful. Tagging repository with version number."
	scmJobs.gitTag("${params.workspace}","${params.targetBuild}","Jenkins: Successful Build ${params.targetBuild}","bitbucket_ssh",true)
	
}

params.openshift = [
    memberships: ['o_linux_dqs_lit'],
    replicas: 1,
    environmentVariables: [
        dev  : [
	        [name: "INFO_APP_ENVIRONMENT", value:"dev"]
	        
	        ,[name: "IM_JAVA_LOGGING_APIKEY_NAME", value:"api-key"]
	        ,[name: "IM_JAVA_LOGGING_URL", value:"http://logging:8080/api/lottery/v1/logging/entry"]
	        
	        ,[name: "INTRALOT_BASEURL", value:"http://apigatewayj.bclc.app.oseqa.l10.intralot.com"] 
            ,[name: "INTRALOT_OAUTH_ACCESSTOKENURL", value: "http://apigatewayj.bclc.app.oseqa.l10.intralot.com/authentication/token"]
            ,[name: "INTRALOT_OAUTH_ACCESSTOKENURLSECURE", value: "true"]
            ,[name: "INTRALOT_CREATEORDER_HEADER_CHANNEL", value: "100"]
            ,[name: "INTRALOT_CREATEORDER_HEADER_OPERATOR", value: "1"]

            ,[name: "ADMINSERVER_URL", value:"http://adminserver:8080"]
            ,[name: "ADMINSERVER_CLIENT_SERVICE_URL", value:"https://createorder-dev.apps.non-prod.ocp.bclc.com/swagger-ui"]
            ,[name: "ADMINSERVER_CLIENT_HEALTH_URL", value:"http://createorder:8080/actuator/health"]
            ,[name: "ADMINSERVER_CLIENT_MGMT_URL", value:"http://createorder:8080/actuator"]

            ,[name: "OAUTH_INTROSPECTION_URL", value:"https://emplidps.bclc.com:443/as/introspect.oauth2"]
            ,[name: "IM_JAVA_AUTHORIZATION_URL", value:"http://authorization:8080/api/lottery/v1/authorization/authorize"]

        ],
        qa   : [  
	        [name: "INFO_APP_ENVIRONMENT", value:"qa"]
	        
	        ,[name: "IM_JAVA_LOGGING_APIKEY_NAME", value:"api-key"]
	        ,[name: "IM_JAVA_LOGGING_URL", value:"http://logging:8080/api/lottery/v1/logging/entry"]
	        
	        ,[name: "INTRALOT_BASEURL", value:"https://apigatewayj.qa.lottery.bclc.com"] 
            ,[name: "INTRALOT_OAUTH_ACCESSTOKENURL", value: "https://apigatewayj.qa.lottery.bclc.com/authentication/token"]
            ,[name: "INTRALOT_OAUTH_ACCESSTOKENURLSECURE", value: "true"]
            ,[name: "INTRALOT_CREATEORDER_HEADER_CHANNEL", value: "100"]
            ,[name: "INTRALOT_CREATEORDER_HEADER_OPERATOR", value: "1"]

            ,[name: "ADMINSERVER_URL", value:"http://adminserver:8080"]
            ,[name: "ADMINSERVER_CLIENT_SERVICE_URL", value:"https://createorder-qa.apps.non-prod.ocp.bclc.com/swagger-ui"]
            ,[name: "ADMINSERVER_CLIENT_HEALTH_URL", value:"http://createorder:8080/actuator/health"]
            ,[name: "ADMINSERVER_CLIENT_MGMT_URL", value:"http://createorder:8080/actuator"]

            ,[name: "OAUTH_INTROSPECTION_URL", value:"https://emplidps.bclc.com:443/as/introspect.oauth2"]
            ,[name: "IM_JAVA_AUTHORIZATION_URL", value:"http://authorization:8080/api/lottery/v1/authorization/authorize"]
        ],
        stg  : [  
        ],
        prod : [  
        ]
    ],
    routes: [
        [
            hostname: "createorder", 
            port: 8080, 
            insecureEdgeTerminationPolicy: "Redirect", 
            annotations: [
                all:["haproxy.router.openshift.io/ip_whitelist: 0.0.0.0/0"]
            ]
        ]
    ],
            /* The following values must be defined on a Secrets object in Openshift directly
				IM_JAVA_LOGGING_APIKEY_VALUE: dGVtcG9yYXJ5
				INTRALOT_OAUTH_CLIENTSECRET: dGVtcG9yYXJ5
				INTRALOT_OAUTH_PASSWORD: dGVtcG9yYXJ5
				INTRALOT_OAUTH_SCOPE: dGVtcG9yYXJ5
				INTRALOT_OAUTH_CLIENTID: dGVtcG9yYXJ5
				INTRALOT_OAUTH_USERNAME: dGVtcG9yYXJ5
				OAUTH_CLIENT_ID: dGVtcG9yYXJ5
				OAUTH_CLIENT_SECRET: dGVtcG9yYXJ5     		                   
            */             
    secrets: [
	    all: ['createorder', 'lgi-integration-services'],
	    dev: [],
	    qa:  [],
	    stg: [],
	    prod:[]
  ],  
]
  
pipelineInitializer.runPipeline(params)