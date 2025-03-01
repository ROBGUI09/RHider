package io.robgui09.rhider;

import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.ChatFormatting;
import java.util.EnumSet;
import net.minecraft.network.chat.Style;

public class LegacyComponentSerializer {
	public static String convertToLegacy(Component component) {
		return convertToLegacy(component.getVisualOrderText());
	}

	public static String convertToLegacy(FormattedCharSequence charSequence) {
		StringBuilder builder = new StringBuilder();

		charSequence.accept(new FormattedCharSink() {
			private final EnumSet<ChatFormatting> formats = EnumSet.noneOf(ChatFormatting.class);
			private boolean first = true;
			private EnumSet<ChatFormatting> oldFormats = EnumSet.noneOf(ChatFormatting.class);

			@Override
			public boolean accept(int idx, Style style, int codePoint) {
				this.formats.clear();
				boolean reset = true;
				if (style.getColor() != null) {
					String serialized = style.getColor().serialize();
					if (serialized.charAt(0) != '#') // Named colors do not have a hex # identifier
						this.formats.add(ChatFormatting.getByName(serialized));
					reset = false; // Setting a color automatically resets
				}

				if (style.isBold())
					this.formats.add(ChatFormatting.BOLD);
				if (style.isItalic())
					this.formats.add(ChatFormatting.ITALIC);
				if (style.isUnderlined())
					this.formats.add(ChatFormatting.UNDERLINE);
				if (style.isStrikethrough())
					this.formats.add(ChatFormatting.STRIKETHROUGH);
				if (style.isObfuscated())
					this.formats.add(ChatFormatting.OBFUSCATED);

				if (!this.formats.equals(this.oldFormats)) {
					if (reset) {
						if (this.first) {
							this.first = false;
						} else {
							builder.append(ChatFormatting.RESET);
						}
					}
					for (ChatFormatting format : this.formats)
						builder.append(format);
					this.oldFormats.clear();
					this.oldFormats.addAll(this.formats);
				}

				builder.appendCodePoint(codePoint);
				return true;
			}
		});

		return builder.toString();
	}
}