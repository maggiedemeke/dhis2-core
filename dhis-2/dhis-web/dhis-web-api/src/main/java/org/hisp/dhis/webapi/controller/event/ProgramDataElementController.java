package org.hisp.dhis.webapi.controller.event;

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
import org.hisp.dhis.program.ProgramDataElement;
import org.hisp.dhis.program.ProgramService;
import org.hisp.dhis.query.Order;
import org.hisp.dhis.query.Query;
import org.hisp.dhis.query.QueryParserException;
import org.hisp.dhis.webapi.controller.AbstractCrudController;
import org.hisp.dhis.webapi.webdomain.WebMetaData;
import org.hisp.dhis.webapi.webdomain.WebOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Lars Helge Overland
 */
@Controller
@RequestMapping( value = "/programDataElements" )
public class ProgramDataElementController
    extends AbstractCrudController<ProgramDataElement>
{
    @Autowired
    private ProgramService programService;

    @Override
    @SuppressWarnings( "unchecked" )
    protected List<ProgramDataElement> getEntityList( WebMetaData metaData, WebOptions options, List<String> filters,
        List<Order> orders, TranslateParams translateParams ) throws QueryParserException
    {
        List<ProgramDataElement> programDataElements;
        Query query = queryService.getQueryFromUrl( ProgramDataElement.class, filters, orders );
        query.setDefaultOrder();

        if ( options.contains( "program" ) )
        {
            String programUid = options.get( "program" );
            programDataElements = programService.getGeneratedProgramDataElements( programUid );
            query.setObjects( programDataElements );
        }

        programDataElements = (List<ProgramDataElement>) queryService.query( query );

        return programDataElements;
    }
}
