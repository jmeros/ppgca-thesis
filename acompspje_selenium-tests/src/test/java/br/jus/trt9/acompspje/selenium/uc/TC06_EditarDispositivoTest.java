package br.jus.trt9.acompspje.selenium.uc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.trt9.acompspje.db.BDUtils;
import br.jus.trt9.acompspje.db.EstadoProcesso;
import br.jus.trt9.acompspje.db.RoteiroPautaSessao;
import br.jus.trt9.acompspje.selenium.telas.PainelAssistente;
import br.jus.trt9.acompspje.selenium.telas.PainelSecretario;
import br.jus.trt9.acompspje.selenium.telas.TelaInicial;

public class TC06_EditarDispositivoTest extends UsuarioUnicoTestTemplate {
	private final String DISPOSITIVO_5000_CARACTERES = "∞ ♫ ♬ ♭ ♮ ♯ ♰ ♱ ✁ ✂ ✃ ✄ ✆ ✇ ✈ ✉ ✌ ✍ ✎ ✏ ✐ ✑ ✒ ✓ ✔ ✕ ✖ ✗ ✘ ✙ ✚ ✛ ✜ ✝ ✞ ✟ ✠ ✡ ✢ ✣ ✤ ✥ ✦ ✧ ✩ ✪ ✫ ✬ ✭ ✮ ✯ ✰ ✱ ▀ ▁ ▂ ▃ ▄ ▅ ▆ ▇ █ ▉ ▊ ▋ ▍ ▎ ▏ ▐ ░ ▒ ▓ ■ □ ▢ ▣ ▤ ▥ ▦ ▧ ▨ ▩ ▪ ▫ ▬ ▭ ▮ ▯ ▰ ▱ ▲ △ ▴ ▵ ▶ ▷ ▸ ▹ ► ▻ ▼ ▽ ▾ ▿ ◀ ◁ ◂ ◃◄ ◅ ◆ ◇ ◈ ◉ ◊ ○ ◌ ◍ ◎ ● ◐ ◑ ◒ ◓ ◔ ◕ ◖ ◗ ◘ ◙ ◚ ◛ ◜ ◝ ◞ ◟ ◠ ◡ ◢ ◣ ◤ ◥ ◦ ◧ ◨ ◩ ◪ ◫ ◬ ◭ ◮ ◯ ☀ ☁ ☂ ☃ ☄ ★ ☆ ☇ ☈ ☉ ☊ ☋ ☌ ☍ ☎ ☏ ☐ ☑ ☒ ☓ ☚ ☛ ☜ ☝ ☞ ☟ ☠ ☡ ☢ ☣ ☤ ☥ ☦ ☧ ☨ ☩ ☪ ☫ ☬ ☭ ☮ ☯ ☰ ☱ ☲ ☳ ☴ ☵ ☶ ☷ ☸ ☹ ☺ ☻ ☼ ☽ ☾ ☿ ♀ ♁ ♂ ♃ ♄ ♅ ♆ ♇ ♈ ♉ ♊ ♋ ♌ ♍ ♎ ♏ ♐ ♑ ♒ ♓ ♔ ♕ ♖ ♗ ♘ ♙ ♚ ♛ ♜ ♝ ♞ ♟ ♠ ♡ ♢ ♣ ♤ ♥ ♦ ♧ ♨ ♩ ♪ ✲ ✳ ✴ ✵ ✶ ✷ ✸ ✹ ✺ ✻ ✼ ✽ ✾ ✿ ❀ ❁ ❂ ❃ ❄ ❅ ❆ ❇ ❈ ❉ ❊ ❋ ❍ ❏ ❐ ❑ ❒ ❖ ❘ ❙ ❚ ❛ ❜ ❝ ❞ ❡ ❢ ❣ ❤ ❥ ❦ ❧ ❶ ❷ ❸ ❹ ❺ ❻ ❼ ❽ ❾ ❿ ➀ ➁ ➂ ➃ ➄ ➅ ➆ ➇ ➈ ➉ ➊ ➋ ➌ ➍ ➎ ➏ ➐ ➑ ➒ ➓ ➔ ➘ ➙ ➚ ➛ ➜ ➝ ➞ ➟ ➠ ➡ ➢ ➣ ➤ ➥ ➦ ➧ ➨ ➩ ➪ ➫ ➬ ➭ ➮ ➯ ➱ ➲ ➳ ➴ ➵ ➶ ➷ ➸ ➹ ➺ ➻ ➼ ➽ ➾ ⟡ ⟦ ⟧ ⟨ ⟩ ⟪ ⟫ ⟰ ⟱ ⟲ ⟳ ⟴ ⟵ ⟿ ⤡ ⤢ ⤣ ⤤ ⤥ ⤦ ⤧ ⤨ ⤩ ⤪ ⤫ ⤬ ⤭ ⤮ ⤯ ⤰ ⤱ ⤲ ⤳ ⤴ ⤵ ⤶ ⤷ ⤸ ⤹ ⤺ ⤻ ⤼ ⤽ ⤾ ⤿ ⥀ ⥁ ⥂ ⥃ ⥄ ⥅ ⥆ ⥇ ⥈ ⥉ ⥊ ⥋ ⥌ ⥍ ⥎ ⥏ ⥐ ⥑ ⥒ ⥓ ⥔ ⥕ ⥖ ⥗ ⥘ ⥙ ⥚ ⥛ ⥜ ⥝ ⥞ ⥟ 2 ⥠ ⥡ ⥢ ⥣ ⥤ ⥥ ⥦ ⥧ ⥨ ⥩ ⥪ ⥫ ⥬ ⥭ ⥮ ⥯ ⥰ ⥱ ⥲ ⥳ ⥴ ⥵ ⥶ ⥷ ⥸ ⥹ ⥺ ⥻ ⥼ ⥽ ⥾ ⥿ ⧼ ⧽ ⨀ ⨁ ⨂ ⨃ ⨄ ⨅ ⨆ ⨇ ⨈ ⨉ 〇 〈 〉 《 》 「 」 『 』 【 】 〒 〔 〕 〖 〗 〘 〙 〚 〛 〜 〝₱ ₲ ₳ ⃒ ⃔ ⃕ ⃖ ⃗ ⃠ ⃡ ⃩ ⃪ ℂ ℊ ℍ ℒ ℕ № ℗ ℙ ℚ ℛ ℜ ℝ ℤ ℰ ℳ ℺ ℽ ℿ ⅀ ⅁ ⅂ ⅃ ⅄ ⅅ ⅆ ⅇ ⅈ ⅉ ⅓ ⅔ ⅕ ⅖ ⅗ ⅘ ⅙ ⅚ ⅛ ⅜ ⅝ ⅞ ⅟ Ⅰ Ⅱ Ⅲ Ⅳ Ⅴ Ⅵ Ⅶ Ⅷ Ⅸ Ⅹ Ⅺ Ⅻ Ⅼ Ⅽ Ⅾ Ⅿ ⅰ ⅱ ⅲ ⅳ ⅴ ⅵ ⅶ ⅷ ⅸ ⅹ ⅺ ⅻ ⅼ ⅽ ⅾ ⅿ ↂ ← ↑ → ↓ ↔ ↕ ↖ ↗ ↘ ↙ ↚ ↛ ↜ ↝ ↞ ↟ ↠ ↡ ↢ ↣ ↤ ↥ ↦ ↧ ↨ ↩ ↪ ↫ ↬ ↭ ↮ ↯ ↰ ↱ ↲ ↳ ↴ ↵ ↶ ↷ ↸ ↹ ↺ ↻ ↼ ↽ ↾ ↿ 2 1 C 0 ⇀ ⇁ ⇂ ⇃ ⇄ ⇅ ⇆ ⇇ ⇈ ⇉ ⇊ ⇋ ⇌ ⇍ ⇎ ⇏ ⇐ ⇑ ⇒ ⇓ ⇔ ⇕ ⇖ ⇗ ⇘ ⇙ ⇚ ⇛ ⇜ ⇝ ⇞ ⇟ ⇠ ⇡ ⇢ ⇣ ⇤ ⇥ ⇦ ⇧ ⇨ ⇩ ⇪ ⇫ ⇬ ⇭ ⇮ ⇯ ⇰ ⇱ ⇲ ⇳⇴ ⇶ ⇷ ⇸ ⇹ ⇺ ⇻ ⇼ ⇽ ⇾ ⇿ ← ↑ → ↓ ↔ ↕ ↖ ↗ ↘ ↙ ↚ ↛ ↜ ↝ ↞ ↟ Ⅰ Ⅱ Ⅲ Ⅳ Ⅴ Ⅵ Ⅶ Ⅷ Ⅸ Ⅹ Ⅺ Ⅻ Ⅼ Ⅽ Ⅾ Ⅿ ⅰ ⅱ ⅲ ⅳ ⅴ ⅵ ⅶ ⅷ ⅸ ⅹ ⅺ ⅻ ⅓ ⅔ ⅕ ⅖ ⅗ ⅘ ⅙ ⅚ ⅛ ⅜ ⅝ ⅞ ⅟ ᴖ ᴗ ᴝ ᴟ ᴥ ᴦ ᴧ ० १ ॰ ৲ ৴ ੦ ૦ ଽ ୹ ఇ ౦ ౧ ಇ ൫ ൬๏ ๐ ໂ ໃ ໄ ༌ ། ༎ ༏ ༐ ༑ ༒ ༼ ༽ ༾ ༿ ོ ∬ ∆ ∇ ∊ ∋ ∍ ∎ ∏ ∐ ∑ ∓ ∔ ∕ ∖ ∘ ∙ ∛ ∜ ∝ ∞ ∟ ∬ ∭ ∳ ∴ ∵ ∶ ∷ ∸ ∹ ∺ ∻ ∼ ∽ ∾ ∿ ≀ ≁ ≂ ≃ ≄ ≅ ≆ ≇ ≈ ≉ ≊ ≋ ≌ ≍ ≎ ≏ ≐ ≑ ≒ ≓ ≔ ≕ ≖ ≗ ≘ ≙ ≚ ≛ ≜ ≝ ≞ ≟ ≠ ≡ ≢ ≣ ≤ ≥ ≦ ≧ ≨ ≩ ≪ ≫ ≬ ≭ ≮ ≯ ≰ ≱ ≲ ≳ ≴ ≵ ≶ ≷ ≸ ≹ ≺ ≻ ≼ ≽ ≾ ≿ ⊀ ⊁ ⊂ ⊃ ⊄ ⊅ ⊆ ⊇ ⊈ ⊉ ⊊ ⊋ ⊌ ⊍ ⊎ ⊏ ⊐ ⊑ ⊒ ⊓ ⊔ ⊕ ⊖ ⊗ ⊘ ⊙ ⊚ ⊛ ⊜ ⊝ ⊞ ⊟ ⊠ ⊡ ⊢ ⊣ ⊤ ⊥ ⊦ ⊧ ⊨ ⊩ ⊪ ⊫ ⊬ ⊭ ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿ ⋀ ⋁ ⋂ ⋃ ⋄ ⋅ ⋇ ⋈ ⋉ ⋊ ⋋ ⋌ ⋍ ⋎ ⋏ ⋐ ⋑ ⋒ ⋓ ⋔ ⋕ ⋖ ⋗ ⋘ ⋙ ⋚ ⋛ ⋜ ⋝ ⋞ ⋟ ⋠ ⋡ ⋢ ⋣ ⋤ ⋥ ⋦ ⋧ ⋨ ⋩ ⋪ ⋫ ⋬ ⋭ ⋮ ⋯ ⋰ ⋱ ⋲ ⋳ ⋴ ⋵ ⋶ ⋷ ⋸ ⋹ ⋺ ⋻ ⋼ ⋽ ⋾ ⋿ ⌀ ⌁ ⌂ ⌃ ⌄ ⌅ ⌆ ⌇ ⌈ ⌉ ⌊ ⌋ ⌌ ⌍ ⌎ ⌏ ⌐ ⌑ ⌒ ⌓ ⌔ ⌕ ⌖ ⌗ ⌘ ⌙ ⌚ ⌛ ⌜ ⌝ ⌞ ⌟ ⌠ ⌡ ⌢ ⌣ ⌤ ⌥ ⌦ ⌧ ⌨ ⟨ ⟩ ⌫ ⌬ ⌭ ⌮ ⌯ ⌰ ⌱ ⌲ ⌳ ⌴ ⌵ ⌶ ⌷ ⌸ ⌹ ⌺ ⌻ ⌼ ⌽ ⌾ ⌿ ⍀ ⍁ ⍂ ⍃ ⍄ ⍅ ⍆ ⍇ ⍈ ⍉ ⍊ ⍋ ⍌ ⍍ ⍎ ⍏ ⍐ ⍑ ⍒ ⍓ ⍔ ⍕ ⍖ ⍗ ⍘ ⍙ ⍚ ⍛ ⍜ ⍝ ⍞ ⍟ ⍠ ⍡ ⍢ ⍣ ⍤ ⍥ ⍦ ⍧ ⍨ ⍩ ⍪ ⍫ ⍬ ⍭ ⍮ ⍯ ⍰ ⍱ ⍲ ⍳ ⎠ ⎡ ⎢ ⎣ ⎤ ⎥ ⎦ ⎧ ⎨ ⎩ ⎪ ⎫ ⎬ ⎭ ⎮ ⎯ ⎰⎱ ⎲ ⎳ ⎴ ⎵ ⎶ ⎛ ⎜ ⎝ ⎞ ⎟ ⏜ ⏝ ⏞ ⏟ ⑀ ⑁ ⑂ ⑃ ⑄ ⑅ ⑆ ⑇ ⑈ ⑉ ⑊ ① ② ③ ④ ⑤ ⑥ ⑦ ⑧ ⑨ ⑩ ⑪ ⑫ ⑬ ⑭ ⑮ ⑯ ⑰ ⑱ ⑲ ⑳ Ⓐ Ⓑ Ⓒ Ⓓ Ⓔ Ⓕ Ⓖ Ⓗ Ⓘ Ⓙ Ⓚ Ⓛ Ⓜ Ⓝ Ⓞ Ⓟ Ⓠ Ⓡ Ⓢ Ⓣ Ⓤ Ⓥ Ⓦ Ⓧ Ⓨ Ⓩ ⓐ ⓑ ⓒ ⓓ ⓔ ⓕ ⓖ ⓗ ⓘ ⓙ ⓚ ⓛ ⓜ ⓝ ⓞ ⓟ ─ ━ │ ┃ ┄ ┅ ┆ ┇ ┈ ┉ ┊ ┋ ┌ ┍ ┎ ┏ ┐ ┑ ┒ ┓ └ ┕ ┖ ┗ ┘ ┙ ┚ ┛├ ┝ ┞ ┟ ┠ ┡ ┢ ┣ ┤┥ ┦ ┧ ┨ ┩ ┪ ┫ ┬ ┭ ┮ ┯ ┰ ┱ ┲ ┳ ┴ ┵ ┶ ┷ ┸ ┹ ┺ ┻ ┼ ┽ ┾ ┿ ╀ ╁ ╂ ╃ ╄ ╅ ╆ ╇ ╈ ╉ ╊ ╋ ╌ ╍ ╎ ╏ ═ ║ ╒ ╓ ╔ ╕╖ ╗ ╘ ╙ ╚ ╛ ╜ ╝ ╞ ╟ ╠ ╡ ╢ ╣ ╤ ╥ ╦ ╧ ╨ ╩ ╪ ╫ ╬ ╭ ╮ ╯ ╰ ╱ ╲ ╳ ╴ ╵ ╶ ╷ ╸ ╹ ╺ ╻ ╼ ╽ ╾ ╿ ⓿ 〞 〟 ︴︵ ︶ ︷ ︸ ︹ ︺ ︻ ︼ ︽ ︾ ﹀ ﹁ ﹂ ﹃ ﹄ ﹙ ﹚ ﹛ ﹜ ﹝ ﹞ ﹟ ￢ ￥ ￦ € � ‚ ƒ „ … † ‡ ˆ ‰ Š ‹ Œ � Ž � � — ™ š › œ � ž Ÿ ¡ ¢ £ ¤ ¥ ¦ § ¨x© ª « ¬ ¬ ® ° ± ² ³ µ ¶ • ¸ ¹ º » ¼ ½ ¾ ¿ Æ × Ø Þ ß æ ÷ ø þ Đ Ħħ Į Į Ŀ ŀ Ł ł Ŋ ŋ Ŧ ŧ ſ ƀ Ɓ Ƃ ƃ Ƅ ƅ Ɔ Ɖ Ɗ Ƌ ƍ Ǝ Ə Ɛ Ƒ ƒ Ɣ ƕ Ɩ Ɨ ƚ ƛ Ɯ Ɲ ƞ Ɵ Ƣ ƣ ƥ Ƨ ƨ Ʃ ƪ Ƭ ƭ Ʊ Ʋ Ƴ ƴ Ƶ ƶ Ʒ Ƹ ƹ ƺ ƻ ƾ ƿ ǁ ǂ Ǥ ǥ Ǯ ǯ Ƕ Ƿ Ƞ ȡ Ȣ ȣ ȴ ȵ ȶ ȷ ȸ ȹ Ⱥ Ȼ ȼ Ƚ Ⱦ ɀ Ɂ ɂ Ƀ Ʉ Ʌ Ɇ ɇ Ɉ ɉ Ɋ ɋ Ɍ ɍ Ɏ ɏ ɐ ɑ ɒ ɓ ɔ ɕ ɖ ɗ ɘ ə ɛ ɜ ɝ ɞ ɟ ɠ ɡ ɢ ɣ ɤ ɥ ɦ ɧ ɨ ɩ ɪ ɫ ɬ ɭ ɮ ɯ ɰ ɱ ɲ ɳ ɵ ɶ ɷ ɸ ɹ ɺ ɻ ɼ ɽ ɾ ɿ ʁ ʃ ʄ ʅ ʆ ʇ ʈ ʉ ʊ ʋ ʌ ʍ ʎ ʑ ʒ ʓ ʔ ʕ ʖ ʗ ʘ ʚ ʝ ʞ ʠ ʡ ʢ ʦ ʧ ʨ ʩ ʪ ʬ ʭ ʮ ʯ ʰ ʱ ʲ ʳ ʴ ʵ ʶ ʷ ʸ ʹ ʺ ʻ ʼ ʽ ʾ ʿ ˀ ˁ ˄ ˅ ˆ ˇ ˈ ˉ ˊ ˋ ˌ ˍ ˎ ˏ ˑ ˒ ˓ ˔ ˕ ˖ ˗ ˘ ˙ ˚ ˛ ˜ ˝ ˝ ˟ ˠ ˡ ˢ ˣ ˤ ˥ ˦ ˧ ˨ ˩ ˪ ˫ ˬ ˭ ˮ ˯ ˰ ˱ ˲ ˳ ˴ ˵ ˶ ˷ ˸ ˹ ˺ ˻ ˼ ˽ ˾ ˿ ̛ ̦ ʹ ͵ ͺ ͻ ͼ ͽ ; ΄ ΅ Δ Θ Ξ Π Σ Φ Ψ Ω έ ί ΰ β γ δ ε ζ θ ι κ λ μ ν ξ ο π ρ ς σ τ υ φ χ ψ ω Ϡ ϡ Ϣ ϣ Ϥ ϥ Ϧ ϧ Ϩ ϩ Ϫ ϫ Ϭ ϭ Ϯ ϯ ϰ ϱ ϵ ϶ Ϸ ϸ ϻ ϼ Ͻ Ͼ Ͽ Ж Ф Ш Ю Я ф Ѡ Ѳ Ѽ ѽ Ѿ Ҩ ҩ Ұ Ӷ Ԑ ԑ Ո շ ۝ ۞ ۩ ۵ ܓ ܟ ݀ ހ މ Ⴙ ᄀ ᄼ ᄽ ᄾ ᄿ ᆍ ᆕ ᆜ † ‡ • ‣ ‴ ‷ ‹ › ‿ ⁀ ₓ ₦ ₩ aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbcccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiijjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm";
	
	/**
	 * Cenário 1 - Secretário edita o conteúdo do processo apregoado, move a seleção para
	 * outro processo da lista e ao retornar ao processo apregoado, o conteúdo do dispositivo
	 * editado está correto.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void editarDispositivoApregoadoComoSecretário() throws IOException, SQLException, ClassNotFoundException {
		// Encontrar processo apregoado e um processo não julgado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		RoteiroPautaSessao processoNãoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher a sessão de teste
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Validar que o processo apregoado está selecionado
		painel.validarProcessoSelecionado(processoApregoado);

		String textoAntigoDispositivo = processoApregoado.getDS_DISPOSITIVO();
		try {
			// Editar o dispositivo do processo apregoado
			processoApregoado.mudarDispositivo("Texto do dispositivo editado pelo Selenium.");
			painel.validarDispositivoEditavel(true)
				.mudarDispositivo(processoApregoado)
				.validarDispositivo(processoApregoado, true);
			
			// Selecionar o processo não julgado
			painel.selecionarProcesso(processoNãoJulgado)
				.validarProcessoSelecionado(processoNãoJulgado)
			
			// Selecionar o processo apregoado novamente e validar o conteúdo do dispositivo
				.selecionarProcesso(processoApregoado)
				.validarProcessoSelecionado(processoApregoado);
		}
		finally {
			// Recuperar o texto anterior do dispositvo no processo
			processoApregoado.mudarDispositivo(textoAntigoDispositivo);
			BDUtils.getInstance().atualizarProcesso(processoApregoado);
		}
	}

	/**
	 * Cenário 2 - Assistente edita o conteúdo do processo julgado, move a seleção para 
	 * outro processo da lista e ao retornar ao processo apregoado, o conteúdo do dispositivo 
	 * editado está correto.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void editarDispositivoJulgadoComoAssistente() throws IOException, SQLException, ClassNotFoundException {
		// Encontrar processos que podem ser editados pelo Assistentes (apregoado, não julgado, etc)
		Set<RoteiroPautaSessao> processosJulgados = new HashSet<RoteiroPautaSessao>();
		processosJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.JULGADO));
		processosJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.REVISAR));
		processosJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.ADIADO));
		processosJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.RETIRADO));
		processosJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.VISTA_MESA));
		processosJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.VISTA_REGIMENTAL));
		RoteiroPautaSessao processoNãoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Entrar no sistema
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher a sessão de teste
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		for (RoteiroPautaSessao processoJulgado : processosJulgados) {
			// Selecionar o processo julgado
			painel.selecionarProcesso(processoJulgado)
				.validarProcessoSelecionado(processoJulgado);

			String textoAntigoDispositivo = processoJulgado.getDS_DISPOSITIVO();
			try {
				// Editar o dispositivo do processo apregoado
				processoJulgado.mudarDispositivo("Texto do dispositivo editado pelo Selenium.");
				painel.validarDispositivoEditavel(true)
					.mudarDispositivo(processoJulgado)
					.validarDispositivo(processoJulgado, true);
				
				// Selecionar o processo não julgado
				painel.selecionarProcesso(processoNãoJulgado)
					.validarProcessoSelecionado(processoNãoJulgado)
				
				// Selecionar o processo apregoado novamente e validar o conteúdo do dispositivo
					.selecionarProcesso(processoJulgado)
					.validarProcessoSelecionado(processoJulgado);
	
				// Selecionar o processo não julgado para evitar que o dispositivo seja sobrescrito
				// após o texto anterior ser recuperado diretamente no banco
				painel.selecionarProcesso(processoNãoJulgado)
					.validarProcessoSelecionado(processoNãoJulgado);
			}
			finally {
				// Recuperar o texto anterior do dispositvo no processo
				processoJulgado.mudarDispositivo(textoAntigoDispositivo);
				BDUtils.getInstance().atualizarProcesso(processoJulgado);
			}
		}
	}

	/**
	 * Cenário 3 - Secretário não pode editar o conteúdo de processos não apregoados. O secretário
	 * tenta editar os processos, porém o conteúdo do dispositivo não é alterado.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void editarDispositivoNaoApregoadoComoSecretário() throws IOException, SQLException, ClassNotFoundException {
		// Encontrar processos não apregoados
		Set<RoteiroPautaSessao> processosNaoApregoados = new HashSet<RoteiroPautaSessao>();
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.ADIADO));
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.JULGADO));
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO));
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.RETIRADO));
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.REVISAR));
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.VISTA_MESA));
		processosNaoApregoados.add(encontrarProcessoPorEstado(EstadoProcesso.VISTA_REGIMENTAL));
		
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher a sessão de teste
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		for (RoteiroPautaSessao processo : processosNaoApregoados) {
			// Selecionar o  processo e validar que ele foi selecionado
			painel.selecionarProcesso(processo)
				.validarProcessoSelecionado(processo)

			// Verificar que o conteúdo do dispositivo não é editável 
				.validarDispositivoEditavel(false);
		}
	}

	/**
	 * Cenário 4 - Assistente não pode editar o conteúdo de processos não julgados. O Assistente
	 * tenta editar os processos, porém o conteúdo do dispositivo não é alterado.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void editarDispositivoNaoJulgadoComoAssistente() throws IOException, SQLException, ClassNotFoundException {
		// Encontrar processos não apregoados
		Set<RoteiroPautaSessao> processosNaoJulgados = new HashSet<RoteiroPautaSessao>();
		processosNaoJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.APREGOADO));
		processosNaoJulgados.add(encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO));
		
		// Entrar no sistema
		PainelAssistente painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher a sessão de teste
			.entrarSessaoComoAssistentePorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		for (RoteiroPautaSessao processo : processosNaoJulgados) {
			// Selecionar o  processo e validar que ele foi selecionado
			painel.selecionarProcesso(processo)
				.validarProcessoSelecionado(processo)
			
			// Verificar que o conteúdo do dispositivo não é editável 
				.validarDispositivoEditavel(false);
		}
	}

	/**
	 * Cenário 5 - Secretário deixa vazio o dispositivo do processo apregoado, move a seleção 
	 * para outro processo da lista e ao retornar ao processo apregoado, o conteúdo do dispositivo
	 * editado está vazio.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void editarDispositivoSemConteudoComoSecretário() throws IOException, SQLException, ClassNotFoundException {
		// Encontrar processo apregoado e um processo não julgado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		RoteiroPautaSessao processoNãoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher a sessão de teste
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Validar que o processo apregoado está selecionado
		painel.validarProcessoSelecionado(processoApregoado);
		String textoAntigoDispositivo = processoApregoado.getDS_DISPOSITIVO();
		
		try {
			// Editar o dispositivo do processo apregoado
			processoApregoado.mudarDispositivo("");
			painel.validarDispositivoEditavel(true)
				.mudarDispositivo(processoApregoado)
				.validarDispositivo(processoApregoado, true);
			
			// Selecionar o processo não julgado
			painel.selecionarProcesso(processoNãoJulgado)
				.validarProcessoSelecionado(processoNãoJulgado)
			
			// Selecionar o processo apregoado novamente e validar o conteúdo do dispositivo
				.selecionarProcesso(processoApregoado)
				.validarProcessoSelecionado(processoApregoado);
		}
		finally {
			// Recuperar o texto anterior do dispositvo no processo
			processoApregoado.mudarDispositivo(textoAntigoDispositivo);
			BDUtils.getInstance().atualizarProcesso(processoApregoado);
		}
	}

	/**
	 * Cenário 6 - Secretário edita o dispositivo do processo apregoado com texto de 5000 caracteres 
	 * contendo também caracteres especiais, move a seleção para outro processo da lista e ao retornar 
	 * ao processo apregoado, o conteúdo do dispositivo editado está correto.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@Test
	public void editarDispositivo5000CaracteresComoSecretário() throws IOException, SQLException, ClassNotFoundException {
		// Encontrar processo apregoado e um processo não julgado
		RoteiroPautaSessao processoApregoado = encontrarProcessoPorEstado(EstadoProcesso.APREGOADO);
		RoteiroPautaSessao processoNãoJulgado = encontrarProcessoPorEstado(EstadoProcesso.NAO_JULGADO);
		
		// Entrar no sistema
		PainelSecretario painel = new TelaInicial(driver)
			.autenticarUsuario()
		
		// Acessar lista de sessões disponíveis e escolher a sessão de teste
			.entrarSessaoComoSecretarioPorNumeroPauta(SESSAO.getID_SESSAO_PJE(), PROCESSOS_PAUTA.size());
		
		// Validar que o processo apregoado está selecionado
		painel.validarProcessoSelecionado(processoApregoado);

		String textoAntigoDispositivo = processoApregoado.getDS_DISPOSITIVO();
		try {
			// Editar o dispositivo do processo apregoado
			processoApregoado.mudarDispositivo(DISPOSITIVO_5000_CARACTERES);
			painel.validarDispositivoEditavel(true)
				.mudarDispositivo(processoApregoado)
				.validarDispositivo(processoApregoado, true);
			
			// Selecionar o processo não julgado
			painel.selecionarProcesso(processoNãoJulgado)
				.validarProcessoSelecionado(processoNãoJulgado)
			
			// Selecionar o processo apregoado novamente e validar o conteúdo do dispositivo
				.selecionarProcesso(processoApregoado)
				.validarProcessoSelecionado(processoApregoado);
		}
		finally {
			// Recuperar o texto anterior do dispositvo no processo
			processoApregoado.mudarDispositivo(textoAntigoDispositivo);
			BDUtils.getInstance().atualizarProcesso(processoApregoado);
		}
	}

}
