/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ws2ten1.jackson.rawvalue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

class RawValueDeserializer extends JsonDeserializer<String> implements ContextualDeserializer {
	
	@Override
	public String deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
		TreeNode tree = jp.getCodec().readTree(jp);
		return tree.toString();
	}
	
	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctx,
			BeanProperty property) throws JsonMappingException {
		if (property != null && property.getMember().getAnnotation(JsonRawValue.class) != null) {
			return this;
		}
		return new StringDeserializer();
	}
}
