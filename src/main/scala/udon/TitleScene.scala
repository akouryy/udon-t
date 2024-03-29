package net.akouryy.udon.view

import scalafx.Includes._
import net.akouryy.sacalaba.Imports._
import net.akouryy.lib._
import java.net.URL
import javax.sound.sampled._
import net.akouryy.tw4s

object TitleScene extends Scene {
  root = new BorderPane {
    vgrow = Priority.Always
    hgrow = Priority.Always

    style = """
              -fx-background-color: #F9D1D3;
              -fx-accent: #753329;
              -fx-focus-color: #FFFFFF;
            """

    center = new GridPane {
      alignment = Pos.Center
      margin = Insets(20)
      hgrow = Priority.Always

      columnConstraints = Array(30, 40, 30) map (x => new ColumnConstraints { percentWidth = x })

      val title = new HBox {
        margin = Insets(20)
        style = "-fx-font-size: 100pt;"
        hgrow = Priority.Always
        alignment = Pos.Center
        effect = new DropShadow {
          color = Color.web("#FFF9F9")
          radius = 25
          spread = 0.25
        }

        children = "udon".map(new TitleText(_))
      }

      val key = new TextField {
        promptText = "API Key (25文字)"
        margin = Insets(5, 0, 5, 0)
        hgrow = Priority.Always
        style <== when(focused || length === 25) choose "" otherwise "-fx-border-color: red;"
      }
      val secret = new TextField {
        promptText = "API Secret (50文字)"
        margin = Insets(5, 0, 5, 0)
        hgrow = Priority.Always
        style <== when(focused || length === 50) choose "" otherwise "-fx-border-color: red;"
      }
      val authorize = new Button {
        text = "認証"
        maxWidth = Double.MaxValue
        margin = Insets(20, 0, 10, 0)
        hgrow = Priority.Always

        onAction = () => {
          var messages = List(
            key.length.value == 25 |!> "API Key は 25 文字で指定してください。",
            secret.length.value == 50 |!> "API Secret は 50 文字で指定してください。"
          ).flatten

          messages match {
            case Nil =>
              val a = new tw4s.Access(key.text.value, secret.text.value)
              try {
                a.showPin()
                new TextInputDialog {
                  title = "PIN 認証"
                  headerText = "認証後に表示される7桁の PIN コードを入力してください。"
                }.showAndWait map (a fetchAccessToken _ map constL(a.twitter updateStatus "test"))
              } catch {
                case e: tw4s.TwitterException =>
                  new Alert(Alert.AlertType.Error) {
                    initModality(Modality.APPLICATION_MODAL)
                    title = "APIキーエラー"
                    headerText = "API Key または API Secret が不正です。"
                    contentText = e.toString
                  }.showAndWait
              }
            case _ =>
              new Alert(Alert.AlertType.Error) {
                initModality(Modality.APPLICATION_MODAL)
                title = "認証フォーマットエラー"
                headerText = "エラー"
                contentText = messages mkString "\n"
              }.showAndWait
          }
        }
      }

      add(title,      0, 0, 3, 1)
      add(key,        0, 1, 3, 1)
      add(secret,     0, 2, 3, 1) // 50
      add(authorize , 1, 3)
    }
  }
}

class TitleText(c: Char) extends Text {
  text = c.toString.toUpperCase

  fill <== when(hover) choose Color.web("#FFFFFF") otherwise Color.web("#753329")
  stroke <== when(hover) choose Color.web("#753329") otherwise Color.web("#00000000")
  strokeWidth = 5

  onMouseClicked = (ev: MouseEvent) => {
    (ev.button match {
      case MouseButton.PRIMARY => Some(1)
      case MouseButton.SECONDARY => Some(2)
      case _ => None
    }) map { n =>
      val audioIn = AudioSystem.getAudioInputStream(new java.io.File(s"./resources/op/$c$n.wav"))
      val clip = AudioSystem.getClip
      clip.open(audioIn)
      clip.start
    }
  }
}
