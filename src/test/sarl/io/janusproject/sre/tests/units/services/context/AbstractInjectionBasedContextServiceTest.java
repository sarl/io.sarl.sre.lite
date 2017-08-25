/*
 * $Id$
 * 
 * Janus platform is an open-source multiagent platform.
 * More details on http://www.janusproject.io
 * 
 * Copyright (C) 2014-2015 Sebastian RODRIGUEZ, Nicolas GAUD, Stéphane GALLAND.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.janusproject.sre.tests.units.services.context;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

import com.google.inject.Injector;

import io.janusproject.sre.services.context.AbstractInjectionBasedContextService;
import io.janusproject.sre.services.context.ContextFactory;
import io.janusproject.sre.services.logging.LoggingService;
import io.sarl.tests.api.Nullable;

/**
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class AbstractInjectionBasedContextServiceTest<T extends AbstractInjectionBasedContextService> extends AbstractContextServiceTest<T> {

	@Nullable
	private Injector injector;

	@Nullable
	private ContextFactory contextFactory;
	
	@Override
	public void setUp() {
		this.injector = mock(Injector.class);
		this.contextFactory = mock(ContextFactory.class);
		when(this.contextFactory.newInstance(any(), any(), anyBoolean())).thenAnswer((it) -> {
			when(this.context.getID()).thenReturn(it.getArgument(0));
			when(this.context.isRootContext()).thenReturn(it.getArgument(2));
			return this.context;
		});
		
		super.setUp();

		this.service.setInjector(this.injector);
		this.service.setContextFactory(this.contextFactory);
	}

	@Override
	public void getServiceDependencies() {
		assertContains(this.service.getServiceDependencies(), LoggingService.class);
	}

	@Override
	public void getServiceWeakDependencies() {
		assertTrue(this.service.getServiceWeakDependencies().isEmpty());
	}

}
