import{M as k,y as O,e as A,aH as M,ah as P,D as b,G as U,b as E,a8 as z,a3 as V,ai as _,T as v,ay as q,V as W,w as L,W as R}from"./index-hBR8ZTfb.js";import{F as Y}from"./Filter-BgpEpE_r.js";const y=new k;function C(x,e){e.clear();const t=e.matrix;for(let r=0;r<x.length;r++){const i=x[r];if(i.globalDisplayStatus<7)continue;const s=i.renderGroup??i.parentRenderGroup;s!=null&&s.isCachedAsTexture?e.matrix=y.copyFrom(s.textureOffsetInverseTransform).append(i.worldTransform):s!=null&&s._parentCacheAsTextureRenderGroup?e.matrix=y.copyFrom(s._parentCacheAsTextureRenderGroup.inverseWorldTransform).append(i.groupTransform):e.matrix=i.worldTransform,e.addBounds(i.bounds)}return e.matrix=t,e}function S(x){return typeof x.getCanvasFilterString=="function"}class X{constructor(){this.skip=!1,this.useClip=!1,this.filters=null,this.container=null,this.bounds=new P,this.cssFilterString=""}}class G{constructor(e){this._filterStack=[],this._filterStackIndex=0,this._savedStates=[],this._alphaMultiplier=1,this._warnedFilterTypes=new Set,this.renderer=e}push(e){const t=this._pushFilterFrame(),r=e.filterEffect.filters;if(t.skip=!1,t.useClip=!1,t.filters=r,t.container=e.container,t.cssFilterString="",r.every(a=>!a.enabled)){t.skip=!0;return}const i=[],s=1;for(const a of r){if(!a.enabled)continue;if(!S(a)){this._warnUnsupportedFilter(a);continue}const o=a.getCanvasFilterString();if(o===null){this._warnUnsupportedFilter(a);continue}o&&i.push(o)}if(i.length===0&&s===1){t.skip=!0;return}t.cssFilterString=i.join(" "),this._calculateFilterArea(e,t.bounds),t.useClip=!!e.filterEffect.filterArea;const n=this.renderer.canvasContext.activeContext,l=n.filter||"none";if(this._savedStates.push({filter:l,alphaMultiplier:this._alphaMultiplier}),t.useClip&&Number.isFinite(t.bounds.width)&&Number.isFinite(t.bounds.height)&&t.bounds.width>0&&t.bounds.height>0){const a=this.renderer.canvasContext.activeResolution||1;n.save(),n.setTransform(1,0,0,1,0,0),n.beginPath(),n.rect(t.bounds.x*a,t.bounds.y*a,t.bounds.width*a,t.bounds.height*a),n.clip()}else t.useClip=!1;t.cssFilterString&&(n.filter=l!=="none"?`${l} ${t.cssFilterString}`:t.cssFilterString)}pop(){const e=this._popFilterFrame();if(e.skip)return;const t=this._savedStates.pop();if(!t)return;const r=this.renderer.canvasContext.activeContext;e.useClip?r.restore():r.filter=t.filter,this._alphaMultiplier=t.alphaMultiplier}generateFilteredTexture({texture:e,filters:t}){if(!(t!=null&&t.length)||t.every(d=>!d.enabled))return e;const r=[],i=1;for(const d of t){if(!d.enabled)continue;if(!S(d)){this._warnUnsupportedFilter(d);continue}const T=d.getCanvasFilterString();if(T===null){this._warnUnsupportedFilter(d);continue}T&&r.push(T)}if(r.length===0&&i===1)return e;const s=O.getCanvasSource(e);if(!s)return e;const n=e.frame,l=e.source._resolution??e.source.resolution??1,a=n.width,o=n.height,f=A.getOptimalCanvasAndContext(a,o,l),{canvas:c,context:u}=f;u.setTransform(1,0,0,1,0,0),u.clearRect(0,0,c.width,c.height),r.length&&(u.filter=r.join(" "));const p=n.x*l,m=n.y*l,h=a*l,F=o*l;return u.drawImage(s,p,m,h,F,0,0,h,F),u.filter="none",u.globalAlpha=1,M(c,a,o,l)}_calculateFilterArea(e,t){if(e.renderables?C(e.renderables,t):e.filterEffect.filterArea?(t.clear(),t.addRect(e.filterEffect.filterArea),t.applyMatrix(e.container.worldTransform)):e.container.getFastGlobalBounds(!0,t),e.container){const r=e.container.renderGroup||e.container.parentRenderGroup,i=r==null?void 0:r.cacheToLocalTransform;i&&t.applyMatrix(i)}}_warnUnsupportedFilter(e){var r;const t=((r=e==null?void 0:e.constructor)==null?void 0:r.name)||"Filter";this._warnedFilterTypes.has(t)||(this._warnedFilterTypes.add(t),console.warn(`CanvasRenderer: filter "${t}" is not supported in Canvas2D and will be skipped.`))}get alphaMultiplier(){return this._alphaMultiplier}_pushFilterFrame(){let e=this._filterStack[this._filterStackIndex];return e||(e=this._filterStack[this._filterStackIndex]=new X),this._filterStackIndex++,e}_popFilterFrame(){return this._filterStackIndex<=0?this._filterStack[0]:(this._filterStackIndex--,this._filterStack[this._filterStackIndex])}destroy(){this._filterStack=null,this._savedStates=null,this._warnedFilterTypes=null,this._alphaMultiplier=1}}G.extension={type:[b.CanvasSystem],name:"filter"};var j=`in vec2 aPosition;
out vec2 vTextureCoord;

uniform vec4 uInputSize;
uniform vec4 uOutputFrame;
uniform vec4 uOutputTexture;

vec4 filterVertexPosition( void )
{
    vec2 position = aPosition * uOutputFrame.zw + uOutputFrame.xy;
    
    position.x = position.x * (2.0 / uOutputTexture.x) - 1.0;
    position.y = position.y * (2.0*uOutputTexture.z / uOutputTexture.y) - uOutputTexture.z;

    return vec4(position, 0.0, 1.0);
}

vec2 filterTextureCoord( void )
{
    return aPosition * (uOutputFrame.zw * uInputSize.zw);
}

void main(void)
{
    gl_Position = filterVertexPosition();
    vTextureCoord = filterTextureCoord();
}
`,N=`in vec2 vTextureCoord;
out vec4 finalColor;
uniform sampler2D uTexture;
void main() {
    finalColor = texture(uTexture, vTextureCoord);
}
`,w=`struct GlobalFilterUniforms {
  uInputSize: vec4<f32>,
  uInputPixel: vec4<f32>,
  uInputClamp: vec4<f32>,
  uOutputFrame: vec4<f32>,
  uGlobalFrame: vec4<f32>,
  uOutputTexture: vec4<f32>,
};

@group(0) @binding(0) var <uniform> gfu: GlobalFilterUniforms;
@group(0) @binding(1) var uTexture: texture_2d<f32>;
@group(0) @binding(2) var uSampler: sampler;

struct VSOutput {
  @builtin(position) position: vec4<f32>,
  @location(0) uv: vec2<f32>
};

fn filterVertexPosition(aPosition: vec2<f32>) -> vec4<f32>
{
    var position = aPosition * gfu.uOutputFrame.zw + gfu.uOutputFrame.xy;

    position.x = position.x * (2.0 / gfu.uOutputTexture.x) - 1.0;
    position.y = position.y * (2.0 * gfu.uOutputTexture.z / gfu.uOutputTexture.y) - gfu.uOutputTexture.z;

    return vec4(position, 0.0, 1.0);
}

fn filterTextureCoord(aPosition: vec2<f32>) -> vec2<f32>
{
    return aPosition * (gfu.uOutputFrame.zw * gfu.uInputSize.zw);
}

@vertex
fn mainVertex(
  @location(0) aPosition: vec2<f32>,
) -> VSOutput {
  return VSOutput(
   filterVertexPosition(aPosition),
   filterTextureCoord(aPosition)
  );
}

@fragment
fn mainFragment(
  @location(0) uv: vec2<f32>,
) -> @location(0) vec4<f32> {
    return textureSample(uTexture, uSampler, uv);
}
`;class $ extends Y{constructor(){const e=U.from({vertex:{source:w,entryPoint:"mainVertex"},fragment:{source:w,entryPoint:"mainFragment"},name:"passthrough-filter"}),t=E.from({vertex:j,fragment:N,name:"passthrough-filter"});super({gpuProgram:e,glProgram:t})}}class I{constructor(e){this._renderer=e}push(e,t,r){this._renderer.renderPipes.batch.break(r),r.add({renderPipeId:"filter",canBundle:!1,action:"pushFilter",container:t,filterEffect:e})}pop(e,t,r){this._renderer.renderPipes.batch.break(r),r.add({renderPipeId:"filter",action:"popFilter",canBundle:!1})}execute(e){e.action==="pushFilter"?this._renderer.filter.push(e):e.action==="popFilter"&&this._renderer.filter.pop()}destroy(){this._renderer=null}}I.extension={type:[b.WebGLPipes,b.WebGPUPipes,b.CanvasPipes],name:"filter"};const D=new q({attributes:{aPosition:{buffer:new Float32Array([0,0,1,0,1,1,0,1]),format:"float32x2",stride:2*4,offset:0}},indexBuffer:new Uint32Array([0,1,2,0,2,3])});class H{constructor(){this.skip=!1,this.inputTexture=null,this.backTexture=null,this.filters=null,this.bounds=new P,this.container=null,this.blendRequired=!1,this.outputRenderSurface=null,this.firstEnabledIndex=-1,this.lastEnabledIndex=-1}}class B{constructor(e){this._filterStackIndex=0,this._filterStack=[],this._filterGlobalUniforms=new z({uInputSize:{value:new Float32Array(4),type:"vec4<f32>"},uInputPixel:{value:new Float32Array(4),type:"vec4<f32>"},uInputClamp:{value:new Float32Array(4),type:"vec4<f32>"},uOutputFrame:{value:new Float32Array(4),type:"vec4<f32>"},uGlobalFrame:{value:new Float32Array(4),type:"vec4<f32>"},uOutputTexture:{value:new Float32Array(4),type:"vec4<f32>"}}),this._globalFilterBindGroup=new V({}),this.renderer=e}get activeBackTexture(){var e;return(e=this._activeFilterData)==null?void 0:e.backTexture}push(e){const t=this.renderer,r=e.filterEffect.filters,i=this._pushFilterData();i.skip=!1,i.filters=r,i.container=e.container,i.outputRenderSurface=t.renderTarget.renderSurface;const s=t.renderTarget.renderTarget.colorTexture.source,n=s.resolution,l=s.antialias;if(r.every(f=>!f.enabled)){i.skip=!0;return}const a=i.bounds;if(this._calculateFilterArea(e,a),this._calculateFilterBounds(i,t.renderTarget.rootViewPort,l,n,1),i.skip)return;const o=this._getPreviousFilterData();this._setupFilterTextures(i,a,t,o)}generateFilteredTexture({texture:e,filters:t}){if(t.every(f=>!f.enabled))return e;const r=this._pushFilterData();this._activeFilterData=r,r.skip=!1,r.filters=t;const i=e.source,s=i.resolution,n=i.antialias,l=r.bounds;if(l.clear(),l.addRect(e.frame),this._calculateFilterBounds(r,l.rectangle,n,s,0),r.skip)return this._popFilterData(),e;r.outputRenderSurface=_.getOptimalTexture({width:l.width,height:l.height,resolution:r.resolution,antialias:r.antialias}),r.backTexture=v.EMPTY,r.inputTexture=e,this.renderer.renderTarget.finishRenderPass(),this._applyFiltersToTexture(r,!0);const o=r.outputRenderSurface;return o.source.alphaMode="premultiplied-alpha",this._popFilterData(),o}pop(){const e=this.renderer,t=this._popFilterData();t.skip||(e.globalUniforms.pop(),e.renderTarget.finishRenderPass(),this._activeFilterData=t,this._applyFiltersToTexture(t,!1),t.blendRequired&&_.returnTexture(t.backTexture),_.returnTexture(t.inputTexture))}getBackTexture(e,t,r){const i=e.colorTexture.source._resolution,s=_.getOptimalTexture({width:t.width,height:t.height,resolution:i});let n=t.minX,l=t.minY;r&&(n-=r.minX,l-=r.minY),n=Math.floor(n*i),l=Math.floor(l*i);const a=Math.ceil(t.width*i),o=Math.ceil(t.height*i);return this.renderer.renderTarget.copyToTexture(e,s,{x:n,y:l},{width:a,height:o},{x:0,y:0}),s}applyFilter(e,t,r,i){const s=this.renderer,n=this._activeFilterData,a=n.outputRenderSurface===r,o=this._findClosestFilterData(),f=o?o.inputTexture.source._resolution:s.renderTarget.rootRenderTarget.colorTexture.source._resolution;let c=0,u=0;a&&o&&(c=o.bounds.minX,u=o.bounds.minY),this._updateFilterUniforms(t,r,n,c,u,f,a,i);const p=e.enabled?e:this._getPassthroughFilter();this._setupBindGroupsAndRender(p,t,s)}calculateSpriteMatrix(e,t){const r=this._activeFilterData,i=e.set(r.inputTexture._source.width,0,0,r.inputTexture._source.height,r.bounds.minX,r.bounds.minY),s=t.worldTransform.copyTo(k.shared),n=t.renderGroup||t.parentRenderGroup;return n&&n.cacheToLocalTransform&&s.prepend(n.cacheToLocalTransform),s.invert(),i.prepend(s),i.scale(1/t.texture.orig.width,1/t.texture.orig.height),i.translate(t.anchor.x,t.anchor.y),i}destroy(){var e;(e=this._passthroughFilter)==null||e.destroy(!0),this._passthroughFilter=null}_getPassthroughFilter(){return this._passthroughFilter??(this._passthroughFilter=new $),this._passthroughFilter}_setupBindGroupsAndRender(e,t,r){if(r.renderPipes.uniformBatch){const i=r.renderPipes.uniformBatch.getUboResource(this._filterGlobalUniforms);this._globalFilterBindGroup.setResource(i,0)}else this._globalFilterBindGroup.setResource(this._filterGlobalUniforms,0);this._globalFilterBindGroup.setResource(t.source,1),this._globalFilterBindGroup.setResource(t.source.style,2),e.groups[0]=this._globalFilterBindGroup,r.encoder.draw({geometry:D,shader:e,state:e._state,topology:"triangle-list"}),r.type===W.WEBGL&&r.renderTarget.finishRenderPass()}_setupFilterTextures(e,t,r,i){if(e.backTexture=v.EMPTY,e.inputTexture=_.getOptimalTexture({width:t.width,height:t.height,resolution:e.resolution,antialias:e.antialias}),e.blendRequired){r.renderTarget.finishRenderPass();const s=r.renderTarget.getRenderTarget(e.outputRenderSurface);e.backTexture=this.getBackTexture(s,t,i==null?void 0:i.bounds)}r.renderTarget.bind({target:e.inputTexture,clear:!0}),r.globalUniforms.push({offset:t})}_updateFilterUniforms(e,t,r,i,s,n,l,a){const o=this._filterGlobalUniforms.uniforms,f=o.uOutputFrame,c=o.uInputSize,u=o.uInputPixel,p=o.uInputClamp,m=o.uGlobalFrame,h=o.uOutputTexture;l?(f[0]=r.bounds.minX-i,f[1]=r.bounds.minY-s):(f[0]=0,f[1]=0),f[2]=e.frame.width,f[3]=e.frame.height,c[0]=e.source.width,c[1]=e.source.height,c[2]=1/c[0],c[3]=1/c[1],u[0]=e.source.pixelWidth,u[1]=e.source.pixelHeight,u[2]=1/u[0],u[3]=1/u[1],p[0]=.5*u[2],p[1]=.5*u[3],p[2]=e.frame.width*c[2]-.5*u[2],p[3]=e.frame.height*c[3]-.5*u[3];const F=this.renderer.renderTarget.rootRenderTarget.colorTexture;m[0]=i*n,m[1]=s*n,m[2]=F.source.width*n,m[3]=F.source.height*n,t instanceof v&&(t.source.resource=null);const d=this.renderer.renderTarget.getRenderTarget(t);this.renderer.renderTarget.bind({target:t,clear:!!a}),t instanceof v?(h[0]=t.frame.width,h[1]=t.frame.height):(h[0]=d.width,h[1]=d.height),h[2]=d.isRoot?-1:1,this._filterGlobalUniforms.update()}_findClosestFilterData(){for(let e=this._filterStackIndex-1;e>=0;e--){const t=this._filterStack[e];if(!t.skip)return t}return null}_calculateFilterArea(e,t){if(e.renderables?C(e.renderables,t):e.filterEffect.filterArea?(t.clear(),t.addRect(e.filterEffect.filterArea),t.applyMatrix(e.container.worldTransform)):e.container.getFastGlobalBounds(!0,t),e.container){const i=(e.container.renderGroup||e.container.parentRenderGroup).cacheToLocalTransform;i&&t.applyMatrix(i)}}_applyFiltersToTexture(e,t){const r=e.inputTexture,i=e.bounds,s=e.filters,n=e.firstEnabledIndex,l=e.lastEnabledIndex;if(this._globalFilterBindGroup.setResource(r.source.style,2),this._globalFilterBindGroup.setResource(e.backTexture.source,3),n===l)s[n].apply(this,r,e.outputRenderSurface,t);else{let a=e.inputTexture;const o=_.getOptimalTexture({width:i.width,height:i.height,resolution:a.source._resolution});let f=o;for(let c=n;c<l;c++){const u=s[c];if(!u.enabled)continue;u.apply(this,a,f,!0);const p=a;a=f,f=p}s[l].apply(this,a,e.outputRenderSurface,t),_.returnTexture(o)}}_calculateFilterBounds(e,t,r,i,s){var d;const n=this.renderer,l=e.bounds,a=e.filters;let o=1/0,f=0,c=!0,u=!1,p=!1,m=!0,h=-1,F=-1;for(let T=0;T<a.length;T++){const g=a[T];if(!g.enabled)continue;if(h===-1&&(h=T),F=T,o=Math.min(o,g.resolution==="inherit"?i:g.resolution),f+=g.padding,g.antialias==="off"?c=!1:g.antialias==="inherit"&&c&&(c=r),g.clipToViewport||(m=!1),!!!(g.compatibleRenderers&n.type)){p=!1;break}if(g.blendRequired&&!(((d=n.backBuffer)==null?void 0:d.useBackBuffer)??!0)){L("Blend filter requires backBuffer on WebGL renderer to be enabled. Set `useBackBuffer: true` in the renderer options."),p=!1;break}p=!0,u||(u=g.blendRequired)}if(!p){e.skip=!0;return}if(m&&l.fitBounds(0,t.width/i,0,t.height/i),l.scale(o).ceil().scale(1/o).pad((f|0)*s),!l.isPositive){e.skip=!0;return}e.antialias=c,e.resolution=o,e.blendRequired=u,e.firstEnabledIndex=h,e.lastEnabledIndex=F}_popFilterData(){return this._filterStackIndex--,this._filterStack[this._filterStackIndex]}_getPreviousFilterData(){for(let e=this._filterStackIndex-2;e>=0;e--){const t=this._filterStack[e];if(!t.skip)return t}return null}_pushFilterData(){let e=this._filterStack[this._filterStackIndex];return e||(e=this._filterStack[this._filterStackIndex]=new H),this._filterStackIndex++,e}}B.extension={type:[b.WebGLSystem,b.WebGPUSystem],name:"filter"};R.add(B,G);R.add(I);
