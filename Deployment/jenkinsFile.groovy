@Library(["jk-pipeline-common-library", "jk-pipeline-common-pipeline"]) _ // import libraries

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

  
pipelineInitializer.runPipeline(params)