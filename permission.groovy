import jenkins.model.*
import hudson.security.*
import groovy.json.JsonSlurper
def inputJson = new File("users.json")
def userlist = new JsonSlurper().parse(inputJson)
def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def strategy = instance.getAuthorizationStrategy();

userlist.main.admin.each { admingroup ->
   hudsonRealm.createAccount("${admingroup}","${admingroup}")
   strategy.add(Jenkins.ADMINISTER, "${admingroup}")
}

userlist.main.developer.each { developergroup ->
    hudsonRealm.createAccount("${developergroup}","${developergroup}")
      //  Job Build and view Permissions
    strategy.add(hudson.model.Hudson.READ,"${developergroup}")
    strategy.add(hudson.model.Item.BUILD,"${developergroup}")
    strategy.add(hudson.model.Item.CANCEL,"${developergroup}")
    strategy.add(hudson.model.Item.DISCOVER,"${developergroup}")
    strategy.add(hudson.model.Item.READ,"${developergroup}")
    strategy.add(hudson.model.Item.WORKSPACE,"${developergroup}")
}
instance.setAuthorizationStrategy(strategy)
instance.setSecurityRealm(hudsonRealm)
instance.save()

