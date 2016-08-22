/**
 * *****************************************************************************
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************
 */
package org.jetbrains.kotlin.resolve.lang.java.newstructure;

import java.util.Collection;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.WildcardType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.FqName;
import org.netbeans.api.java.source.TypeMirrorHandle;
import org.netbeans.api.project.Project;

/**
 *
 * @author Alexander.Baratynski
 */
public class NetBeansJavaType implements JavaType, JavaAnnotationOwner {

    private final TypeMirrorHandle handle;
    private final Project project;
    
    public NetBeansJavaType(TypeMirrorHandle handle, Project project) {
        this.handle = handle;
        this.project = project;
    }
    
    public TypeMirrorHandle getHandle() {
        return handle;
    }
    
    public Project getProject() {
        return project;
    }
    
    public static NetBeansJavaType create(@NotNull TypeMirrorHandle type, Project project){
        if (type.getKind().isPrimitive() || type.toString().equals("void")){
            return new NetBeansJavaPrimitiveType(type, project);
        } else if (type.getKind() == TypeKind.ARRAY){
            return new NetBeansJavaArrayType((TypeMirrorHandle<ArrayType>) type, project);
        } else if (type.getKind() == TypeKind.DECLARED || 
                type.getKind() == TypeKind.TYPEVAR){
            return new NetBeansJavaClassifierType(type, project);
        } else if (type.getKind() == TypeKind.WILDCARD){
            return new NetBeansJavaWildcardType((TypeMirrorHandle<WildcardType>) type, project);
        } 
        else {
            throw new UnsupportedOperationException("Unsupported NetBeans type: " + type);
        }
    }
    
    @Override
    public Collection<JavaAnnotation> getAnnotations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JavaAnnotation findAnnotation(FqName fqname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return false;
    }
    
    @Override
    public int hashCode(){
        return handle.hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        return obj instanceof NetBeansJavaType && handle.equals(((NetBeansJavaType) obj).getHandle());
    }
    
}
