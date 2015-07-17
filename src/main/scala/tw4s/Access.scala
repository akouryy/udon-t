package net.akouryy.tw4s

class Access(apiKey: String, apiSecret: String) {
  val conf = new twitter4j.conf.ConfigurationBuilder
  conf.setOAuthConsumerKey(apiKey)
  conf.setOAuthConsumerSecret(apiSecret)
  // conf.setGZIPEnabled(false)

  val oauth = new twitter4j.TwitterFactory(conf.build).getInstance

  def showPin() {
    java.awt.Desktop.getDesktop().browse(java.net.URI.create(
      oauth.getOAuthRequestToken().getAuthorizationURL()
    ))
  }

  def fetchAccessToken(pin: String) = {
    try {
      Some(oauth.getOAuthAccessToken(pin))
    } catch {
      case _: twitter4j.TwitterException => None
    }
  }
}
