package com.handoitadsf.line.group_guard

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import groovy.util.logging.Slf4j
import line.thrift.Contact
import line.thrift.ContactSetting

/**
 * Created by someone on 3/25/2017.
 */
@Slf4j
class Demo {
    private final InMemoryStorage storage
    private final Config config
    private final List malicious = []
    public static void main(String[] args) throws Exception {
        new Demo();
    }

    public Demo() {
        storage = new InMemoryStorage();
        config = ConfigFactory.load("config.conf");
        loadAccounts();
        loadGroups()
        def guard = new Guard(storage)
        guard.start()
        log.info("Sleep a while...")
        sleep(1000 * 30)
        log.info("Starting malicious accounts...")
        malicious.each { List entry ->
            def account = entry[0] as Account
            account.login()
            def overthrows = entry[1] as List<String>
            overthrows.each {String overthrow ->
                def group = account.getGroup(overthrow)
                if (group == null) {
                    log.error("Account ${account.mid} isn't a member of group $overthrow")
                    return
                }
                def otherMemberIds = group.getMembers()
                        .collect({it.getMid()})
                        .findAll {it != account.mid}
                try {
                    otherMemberIds.each {def otherMemberId ->
                        account.kickOutFromGroup(overthrow, otherMemberId)
                    }
                } catch (Exception ex) {
                    log.error("Fail to overthrow group $overthrow", ex)
                }

            }
        }
    }

    private void loadAccounts() {
        def accountsConf = config.getConfigList("accounts");
        accountsConf.each { def accountConf ->
            String mid = accountConf.getString("mid")
            String email = accountConf.getString("email")
            String password = accountConf.getString("password")
            String certificate = accountConf.getString("certificate")
            String authToken = accountConf.getString("authToken")
            List<String> overthrow = accountConf.getStringList("overthrow")
            AccountCredential credential = new AccountCredential()
            credential.setEmail(email)
            credential.setPassword(password)
            credential.setCertificate(certificate)
            credential.setAuthToken(authToken)
            if (overthrow.isEmpty()) {
                storage.setAccountCredential(mid, credential)
            } else {
                def account = new Account(credential)
                malicious << [account, overthrow]
            }
        }
    }

    private void loadGroups() {
        def groupsConf = config.getConfigList("groups")
        groupsConf.each { def groupConf ->
            String groupId = groupConf.getString("id")
            List<String> defenders = groupConf.getStringList("defenders")
            List<String> supporters = groupConf.getStringList("supporters")
            defenders.each { String defender ->
                storage.addRole(new Relation(defender, groupId), Role.DEFENDER)
            }
            supporters.each { def supporter ->
                storage.addRole(new Relation(supporter, groupId), Role.SUPPORTER)
            }
        }
    }
}