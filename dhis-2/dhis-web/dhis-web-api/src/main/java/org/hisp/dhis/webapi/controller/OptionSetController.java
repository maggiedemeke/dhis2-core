package org.hisp.dhis.webapi.controller;

/*
 * Copyright (c) 2004-2016, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.dhis.dxf2.common.TranslateParams;
import org.hisp.dhis.option.OptionSet;
import org.hisp.dhis.schema.descriptors.OptionSetSchemaDescriptor;
import org.hisp.dhis.webapi.webdomain.WebOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Morten Olav Hansen <mortenoh@gmail.com>
 */
@Controller
@RequestMapping( value = OptionSetSchemaDescriptor.API_ENDPOINT )
public class OptionSetController
    extends AbstractCrudController<OptionSet>
{
    @Override
    protected void postProcessEntities( List<OptionSet> optionSets, WebOptions options, Map<String, String> parameters, TranslateParams translateParams )
    {
        if ( translateParams.isTranslate() )
        {
            optionSets.forEach( o -> translateOptions( o, translateParams ) );
        }
    }

    @Override
    protected void postProcessEntity( OptionSet optionSet, WebOptions options, Map<String, String> parameters, TranslateParams translateParams ) throws Exception
    {
        if ( translateParams.isTranslate() )
        {
            translateOptions( optionSet, translateParams );
        }
    }

    private void translateOptions( OptionSet optionSet, TranslateParams translateParams )
    {
        if ( translateParams.defaultLocale() )
        {
            i18nService.internationalise( optionSet.getOptions() );
        }
        else
        {
            i18nService.internationalise( optionSet.getOptions(), translateParams.getLocale() );
        }
    }
}
