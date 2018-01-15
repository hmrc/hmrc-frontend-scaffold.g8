#!/bin/bash

echo "Applying migration $className;format="snake"$"

echo "Adding routes to conf/app.routes"

echo "" >> ../conf/app.routes
echo "GET        /$className;format="decap"$                       controllers.$className$Controller.onPageLoad(mode: Mode = NormalMode)" >> ../conf/app.routes
echo "POST       /$className;format="decap"$                       controllers.$className$Controller.onSubmit(mode: Mode = NormalMode)" >> ../conf/app.routes

echo "GET        /change$className$                       controllers.$className$Controller.onPageLoad(mode: Mode = CheckMode)" >> ../conf/app.routes
echo "POST       /change$className$                       controllers.$className$Controller.onSubmit(mode: Mode = CheckMode)" >> ../conf/app.routes

echo "Adding messages to conf.messages"
echo "" >> ../conf/messages.en
echo "$className;format="decap"$.title = $className;format="decap"$" >> ../conf/messages.en
echo "$className;format="decap"$.heading = $className;format="decap"$" >> ../conf/messages.en
echo "$className;format="decap"$.field1 = Field 1" >> ../conf/messages.en
echo "$className;format="decap"$.field2 = Field 2" >> ../conf/messages.en
echo "$className;format="decap"$.checkYourAnswersLabel = $className;format="decap"$" >> ../conf/messages.en
echo "$className;format="decap"$.error.field1.required = Enter field1" >> ../conf/messages.en
echo "$className;format="decap"$.error.field2.required = Enter field2" >> ../conf/messages.en
echo "$className;format="decap"$.error.field1.length = field1 must be $field1MaxLength$ characters or less" >> ../conf/messages.en
echo "$className;format="decap"$.error.field2.length = field2 must be $field2MaxLength$ characters or less" >> ../conf/messages.en

echo "Adding helper line into UserAnswers"
awk '/class/ {\
     print;\
     print "  def $className;format="decap"$: Option[$className$] = cacheMap.getEntry[$className$]($className$Id.toString)";\
     print "";\
     next }1' ../app/utils/UserAnswers.scala > tmp && mv tmp ../app/utils/UserAnswers.scala

echo "Adding helper method to CheckYourAnswersHelper"
awk '/class/ {\
     print;\
     print "";\
     print "  def $className;format="decap"$: Option[AnswerRow] = userAnswers.$className;format="decap"$ map {";\
     print "    x => AnswerRow(\"$className;format="decap"$.checkYourAnswersLabel\", s\"\${x.field1} \${x.field2}\", false, routes.$className$Controller.onPageLoad(CheckMode).url)";\
     print "  }";\
     next }1' ../app/utils/CheckYourAnswersHelper.scala > tmp && mv tmp ../app/utils/CheckYourAnswersHelper.scala

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/

echo "Migration $className;format="snake"$ completed"
