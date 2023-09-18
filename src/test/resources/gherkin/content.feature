Feature: The content should be saved
  As a teacher
  I want to create a content for my lesson
  So that I show to class

  Scenario Outline: Teacher create successfully the content
    Given Content <contentName>
    And type <contentType>
    And link <contentLink>
    When Teacher makes call to POST /contents endpoint
    Then content should be saved

    Examples:
      |contentName | contentType | contentLink|
      |"unsupervised learning" | DOC| "https://google.om" |

