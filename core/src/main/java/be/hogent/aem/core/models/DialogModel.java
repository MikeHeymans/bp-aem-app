/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package be.hogent.aem.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.settings.SlingSettingsService;

import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class DialogModel {

    @Inject
    @Named("sling:resourceType")
    @Default(values = "No resourceType")
    protected String resourceType;
    @Inject
    private SlingSettingsService settings;
    @Inject
    @Named("title")
    @Optional
    private String title;
    @Inject
    @Named("text")
    @Optional
    private String text;
    @Inject
    @Named("size")
    @Default(values = "4")
    private String size;

    public String getTitle() {
        return StringUtils.isNotBlank(title) ? title : "Please provide a title";
    }

    public String getText() {
        return StringUtils.isNotBlank(text) ? text : "Please provide a Test";
    }

    public String getColWidth() {
        return "col-xs-" + size;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(title) && StringUtils.isNotBlank(text);
    }
}
