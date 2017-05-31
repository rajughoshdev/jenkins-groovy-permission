import jenkins.model.*
import hudson.security.*
import groovy.json.JsonSlurper

def currentDir = build.workspace.getRemote()
def inputJson = new File(currentDir+"/users.json")
def userlist = new JsonSlurper().parse(inputJson)

println "here are the admins"
userlist.admin.each { println(it) }

println "here are the developers"
userlist.developer.each { println(it) }
