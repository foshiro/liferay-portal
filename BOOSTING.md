# Custom boosting experiments

The branch [BOOSTING](https://github.com/brandizzi/liferay-portal/commits/BOOSTING) is an
experimental branch to investigate custom boosting to queries.

## [LPS-87306](https://issues.liferay.com/browse/LPS-87306) Custom Boost

This spike asks for a feature to boost fields by arbitrary values not found in 
the query. It can be done by changing settings in [this page in 
localhost](http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_mvcRenderCommandName=%2Fedit_configuration&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_factoryPid=com.liferay.portal.search.configuration.CustomRelevanceConfiguration&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_pid=com.liferay.portal.search.configuration.CustomRelevanceConfiguration). 
There we have various boostings, eacho one a line in the form

    <field name> ":" <matching-value> + ":" <boost-value>

eg

    title_en_US:exampl,test:20.0

Then any doc with a `title_en_US` field containing either "exampl" or "test" 
will be boosted by 20.0. (Note we use "exampl" because right now we use a term 
criterion to make the match, and "example" would fail to match with the 
analyzed field.)

## [LPS-87882](https://issues.liferay.com/browse/LPS-87882) Range criteria in Custom Boost

Here we make some changes to boost values based on a range. First we add a 
`wordCount` field to all DL files, with the number of words in its text 
document. Then we extend the boost syntax above to support ranges:

    <field name> ":" "[" <lower-bound> ".." <upper-bound> "]" ":" <boost-value>

For example:

    wordCount_sortable:[50..150]:40

Here any document with 50 to 150 words will be boosted by 40. (We used 
`wordCount_sortable` here because `wordCount` is always a string in the tests 
we did.)


## [LPS-86596](https://issues.liferay.com/browse/LPS-86596) Custom Filter Widget

This adds a custom filter widget. One just adds the widget to the page, goes to 
its configuration dialog and define a field and a string. Only documents with 
the field matching this string will appear in the results.

## [LPS-87581](https://issues.liferay.com/browse/LPS-87581) Custom Boost Widget

This adds a custom boost widget. One just adds the widget to the page, goes to 
its configuration dialog and define a field, a string and a boost value. 
Documents with fields matching this string will be boosted by the value.
