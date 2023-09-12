Feature: The content should be saved
  Scenario Outline: Teacher create successfully the content
    Given Content "<contentName>"
    And type "<contentType>"
    And link "<contentLink>"
    When Teacher makes call to POST /contents endpoint
    Then content should be saved

    Examples:
      |contentName | unsupervised learning |
      |contentType | DOC |
      |contentLink | https://google.com |

