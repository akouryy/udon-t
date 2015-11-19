package net.akouryy.tw4s

import net.akouryy.lib._

class Access(apiKey: String, apiSecret: String) {
  val conf = new twitter4j.conf.ConfigurationBuilder
  conf setOAuthConsumerKey apiKey
  conf setOAuthConsumerSecret apiSecret
  // conf setGZIPEnabled false

  val twitter = new twitter4j.TwitterFactory(conf.build).getInstance

  def showPin() {
    java.awt.Desktop.getDesktop browse java.net.URI.create(twitter.getOAuthRequestToken.getAuthorizationURL)
  }

  def fetchAccessToken(pin: String) = {
    try {
      Some(twitter getOAuthAccessToken pin)
    } catch {
      case _: twitter4j.TwitterException => None
    }
  }
}
