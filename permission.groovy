import jenkins.model.*
import hudson.security.*
import groovy.json.JsonSlurper

def currentDir = build.workspace.getRemote()
def inputJson = new File(currentDir+"/users.json")
def userlist = new JsonSlurper().parse(inputJson)

def instance = Jenkins.getInstance()
//def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def strategy = instance.getAuthorizationStrategy();

userlist.admin.each { admingroup ->
//    hudsonRealm.createAccount("${admingroup}","${admingroup}")
    strategy.add(Jenkins.ADMINISTER, "${admingroup}")
}

userlist.developer.each { developergroup ->
//hudsonRealm.createAccount("${developergroup}","${developergroup}")
      //  Job Build and view Permissions
    strategy.add(hudson.model.Hudson.READ,"${developergroup}")
    strategy.add(hudson.model.Item.BUILD,"${developergroup}")
    strategy.add(hudson.model.Item.CANCEL,"${developergroup}")
    strategy.add(hudson.model.Item.DISCOVER,"${developergroup}")
    strategy.add(hudson.model.Item.READ,"${developergroup}")
    strategy.add(hudson.model.Item.WORKSPACE,"${developergroup}")
}
instance.setAuthorizationStrategy(strategy)
//instance.setSecurityRealm(hudsonRealm)
instance.save()
