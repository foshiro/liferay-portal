<#ftl strip_whitespace=true>

<#--
Use computer number format to prevent issues with locale settings. See
LPS-30525.
-->

<#setting number_format = "computer">

<#assign
	css_main_file = ""
	is_signed_in = false
	js_main_file = ""
	is_setup_complete = false
/>

<#if user??>
	<#assign is_setup_complete = user.isSetupComplete() />
</#if>

<#if themeDisplay??>
	<#assign css_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeCss()}/main.css")) />
	<#assign is_signed_in = themeDisplay.isSignedIn() />
	<#assign js_main_file = htmlUtil.escape(portalUtil.getStaticResourceURL(request, "${themeDisplay.getPathThemeJavaScript()}/main.js")) />

	<#if !is_setup_complete>
		<#assign is_setup_complete = themeDisplay.isImpersonated() />
	</#if>
</#if>

<#function max x y>
	<#if x < y>
		<#return y>
	<#else>
		<#return x>
	</#if>
</#function>

<#function min x y>
	<#if x <= y>
		<#return x>
	<#else>
		<#return y>
	</#if>
</#function>

<#macro breadcrumbs
	default_preferences = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		portletProviderAction=portletProviderAction.VIEW
		portletProviderClassName="com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry"
	/>
</#macro>

<#macro control_menu>
	<#if is_setup_complete && is_signed_in>
		<@liferay_product_navigation["control-menu"] />
	</#if>
</#macro>

<#macro css
	file_name
>
	<#if file_name == css_main_file>
		<link class="lfr-css-file" href="${file_name}" id="mainLiferayThemeCSS" rel="stylesheet" type="text/css" />
	<#else>
		<link class="lfr-css-file" href="${file_name}" rel="stylesheet" type="text/css" />
	</#if>
</#macro>

<#macro date
	format
>
${dateUtil.getCurrentDate(format, locale)}</#macro>

<#macro js
	file_name
>
	<#if file_name == js_main_file>
		<script id="mainLiferayThemeJavaScript" src="${file_name}" type="text/javascript"></script>
	<#else>
		<script src="${file_name}" type="text/javascript"></script>
	</#if>
</#macro>

<#macro language
	key
>
${languageUtil.get(locale, key)}</#macro>

<#macro language_format
	arguments
	key
>
${languageUtil.format(locale, key, arguments)}</#macro>

<#macro languages
	default_preferences = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		portletProviderAction=portletProviderAction.VIEW
		portletProviderClassName="com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry"
	/>
</#macro>

<#macro navigation_menu
	default_preferences = ""
	instance_id = ""
>
	<@liferay_portlet["runtime"]
		defaultPreferences=default_preferences
		instanceId=instance_id
		portletProviderAction=portletProviderAction.VIEW
		portletProviderClassName="com.liferay.portal.kernel.theme.NavItem"
	/>
</#macro>

<#macro search
	default_preferences = ""
>
	<#if is_setup_complete>
		<fieldset class="fieldset">
			<form name="searchFm" action="/web/guest/search">
				<div class="form-group form-group-inline input-text-wrapper">
					<input class="field search-input form-control"  name="q" placeholder="Search" title="Search" type="text" value="" size="30">
				</div>

				<div class="lfr-ddm-field-group lfr-ddm-field-group-inline field-wrapper">
					<span class="icon-monospaced">
						<a href="javascript:;" target="_self" class=" lfr-icon-item taglib-icon"  onclick="document.searchFm.submit();">
							<span class="" id="">
								<svg class="lexicon-icon lexicon-icon-search" focusable="false" role="img" title="" viewBox="0 0 512 512">
									<title>search</title>
									<path
										class="lexicon-icon-outline"
										d="M503.254 467.861l-133.645-133.645c27.671-35.13 44.344-79.327 44.344-127.415 0-113.784-92.578-206.362-206.362-206.362s-206.362 92.578-206.362 206.362 92.578 206.362 206.362 206.362c47.268 0 90.735-16.146 125.572-42.969l133.851 133.851c5.002 5.002 11.554 7.488 18.106 7.488s13.104-2.486 18.106-7.488c10.004-10.003 10.004-26.209 0.029-36.183zM52.446 206.801c0-85.558 69.616-155.173 155.173-155.173s155.174 69.616 155.174 155.173-69.616 155.173-155.173 155.173-155.173-69.616-155.173-155.173z" />
								</svg>
							</span>
							<span class="taglib-text hide-accessible"></span>
						</a>
					</span>
				</div>
			</form>
		</fieldset>
	</#if>
</#macro>

<#macro silently
	foo
>
	<#assign foo = foo />
</#macro>

<#macro user_personal_bar>
	<#if is_setup_complete || !is_signed_in>
		<@liferay_portlet["runtime"]
			portletProviderAction=portletProviderAction.VIEW
			portletProviderClassName="com.liferay.admin.kernel.util.PortalUserPersonalBarApplicationType$UserPersonalBar"
		/>
	</#if>
</#macro>